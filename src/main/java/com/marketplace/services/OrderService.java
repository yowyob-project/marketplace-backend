package com.marketplace.services;

import com.marketplace.dtos.response.OrderResponseDTO;
import com.marketplace.entities.Order;
import com.marketplace.enums.OrderStatus;
import com.marketplace.enums.PaymentStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public interface OrderService {
    OrderResponseDTO createOrderFromCart(UUID userId);
    OrderResponseDTO getOrder(UUID orderId);
    List<OrderResponseDTO> getUserOrders(UUID userId);
    OrderResponseDTO updateOrderStatus(UUID orderId, OrderStatus status);
    OrderResponseDTO updatePaymentStatus(UUID orderId, PaymentStatus paymentStatus);
    void cancelOrder(UUID orderId);
    public Order getOrderByPaymentReference(String paymentReference);
}