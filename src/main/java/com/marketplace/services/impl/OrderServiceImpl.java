package com.marketplace.services.impl;

import com.marketplace.dtos.response.OrderResponseDTO;
import com.marketplace.dtos.response.OrderItemResponseDTO;
import com.marketplace.entities.Order;
import com.marketplace.entities.OrderItem;
import com.marketplace.entities.Cart;
import com.marketplace.entities.CartItem;
import com.marketplace.enums.OrderStatus;
import com.marketplace.enums.PaymentStatus;
import com.marketplace.exceptions.PaymentException;
import com.marketplace.exceptions.ResourceNotFoundException;
import com.marketplace.repositories.OrderRepository;
import com.marketplace.repositories.OrderItemRepository;
import com.marketplace.services.CartService;
import com.marketplace.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private static final int MAX_PAYMENT_ATTEMPTS = 3;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CartService cartService;

    @Override
    @Transactional
    public OrderResponseDTO updatePaymentStatus(UUID orderId, PaymentStatus paymentStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        // Initialize payment attempts if null
        if (order.getPaymentAttempts() == null) {
            order.setPaymentAttempts(0);
        }

        // Save old status for comparison
        PaymentStatus oldPaymentStatus = order.getPaymentStatus();

        switch (paymentStatus) {
            case SUCCESS:
                if (order.getStatus() == OrderStatus.PENDING) {
                    order.setStatus(OrderStatus.PAID);
                }
                break;

            case FAILED:
                // Increment payment attempts
                order.setPaymentAttempts(order.getPaymentAttempts() + 1);

                // Check if max attempts reached
                if (order.getPaymentAttempts() >= MAX_PAYMENT_ATTEMPTS) {
                    order.setStatus(OrderStatus.CANCELLED);
                    order.setPaymentStatus(PaymentStatus.CANCELLED);
                    throw new PaymentException("Maximum payment attempts reached. Order cancelled.");
                }
                break;

            case CANCELLED:
                order.setStatus(OrderStatus.CANCELLED);
                break;

            case REFUNDED:
                order.setStatus(OrderStatus.REFUNDED);
                break;

            case PENDING:
                if (oldPaymentStatus == PaymentStatus.SUCCESS) {
                    order.setStatus(OrderStatus.PENDING);
                }
                break;
        }

        order.setPaymentStatus(paymentStatus);
        order.setUpdatedAt(LocalDateTime.now());

        order = orderRepository.save(order);
        return convertToOrderDTO(order);
    }

    // Add method to get payment attempts
    public int getPaymentAttempts(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        return order.getPaymentAttempts() != null ? order.getPaymentAttempts() : 0;
    }

    @Override
    @Transactional
    public OrderResponseDTO createOrderFromCart(UUID userId) {
        // Get cart and items
        Cart cart = cartService.getCartEntity(userId);
        List<CartItem> cartItems = cartService.getCartItems(cart.getId());

        if (cartItems.isEmpty()) {
            throw new IllegalStateException("Cannot create order from empty cart");
        }

        // Create order
        Order order = new Order();
        order.setId(UUID.randomUUID());
        order.setUserId(userId);
        order.setOrderNumber(generateOrderNumber());
        order.setTotalAmount(cart.getTotalAmount());
        order.setStatus(OrderStatus.PENDING);
        order.setPaymentStatus(PaymentStatus.PENDING);
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());

        Order savedOrder = orderRepository.save(order);

        // Create order items
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setId(UUID.randomUUID());
            orderItem.setOrderId(savedOrder.getId());
            orderItem.setProductId(cartItem.getProductId());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setUnitPrice(cartItem.getUnitPrice());
            orderItem.setSubtotal(cartItem.getUnitPrice()
                    .multiply(java.math.BigDecimal.valueOf(cartItem.getQuantity())));

            orderItemRepository.save(orderItem);
        }

        // Clear cart
        cartService.clearCart(userId);

        return convertToOrderDTO(savedOrder);
    }

    @Override
    public OrderResponseDTO getOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        return convertToOrderDTO(order);
    }

    @Override
    public List<OrderResponseDTO> getUserOrders(UUID userId) {
        return orderRepository.findByUserId(userId).stream()
                .map(this::convertToOrderDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public OrderResponseDTO updateOrderStatus(UUID orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        order.setStatus(status);
        order.setUpdatedAt(LocalDateTime.now());
        order = orderRepository.save(order);

        return convertToOrderDTO(order);
    }

    @Override
    @Transactional
    public void cancelOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        order.setStatus(OrderStatus.CANCELLED);
        order.setPaymentStatus(PaymentStatus.CANCELLED);
        order.setUpdatedAt(LocalDateTime.now());
        orderRepository.save(order);
    }

    private String generateOrderNumber() {
        return "ORD-" + System.currentTimeMillis();
    }

    private OrderResponseDTO convertToOrderDTO(Order order) {
        List<OrderItem> items = orderItemRepository.findByOrderId(order.getId());

        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setId(order.getId());
        dto.setUserId(order.getUserId());
        dto.setOrderNumber(order.getOrderNumber());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setStatus(order.getStatus());
        dto.setPaymentStatus(order.getPaymentStatus());
        dto.setCreatedAt(order.getCreatedAt());
        dto.setUpdatedAt(order.getUpdatedAt());
        dto.setItems(convertToOrderItemDTOs(items));

        return dto;
    }

    private List<OrderItemResponseDTO> convertToOrderItemDTOs(List<OrderItem> items) {
        return items.stream()
                .map(this::convertToOrderItemDTO)
                .collect(Collectors.toList());
    }

    private OrderItemResponseDTO convertToOrderItemDTO(OrderItem item) {
        OrderItemResponseDTO dto = new OrderItemResponseDTO();
        dto.setId(item.getId());
        dto.setProductId(item.getProductId());
        dto.setQuantity(item.getQuantity());
        dto.setUnitPrice(item.getUnitPrice());
        dto.setSubtotal(item.getSubtotal());
        return dto;
    }

    public Order getOrderByPaymentReference(String paymentReference) {
        return orderRepository.findByPaymentReference(paymentReference);
    }
}