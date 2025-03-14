package com.marketplace.services.impl;

import com.marketplace.entities.Cart;
import com.marketplace.entities.CartItem;
import com.marketplace.entities.Order;
import com.marketplace.entities.OrderItem;
import com.marketplace.enums.OrderStatus;
import com.marketplace.enums.PaymentStatus;
import com.marketplace.exceptions.ResourceNotFoundException;
import com.marketplace.models.ProductConverter;
import com.marketplace.models.ProductPostRequest;
import com.marketplace.models.ProductPostResponse;
import com.marketplace.repositories.CartItemRepository;
import com.marketplace.repositories.CartRepository;
import com.marketplace.repositories.OrderItemRepository;
import com.marketplace.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class CartToOrderConverter {

    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final WebClient webClient;

    @Autowired
    public CartToOrderConverter(CartRepository cartRepository, CartItemRepository cartItemRepository, OrderRepository orderRepository, OrderItemRepository orderItemRepository, WebClient webClient) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.webClient = webClient;
    }

    // Conversion de Cart à Order
    public Order convertCartToOrder(Cart cart) {
        // Créer un Order
        Order order = new Order();
        order.setId(UUID.randomUUID()); // Crée un nouvel UUID pour l'order
        order.setUserId(cart.getUserId());
        order.setOrderNumber(UUID.randomUUID().toString()); // Génère un numéro de commande
        order.setTotalAmount(cart.getTotalAmount());
        order.setStatus(OrderStatus.PENDING); // Par exemple, on peut définir un statut de commande par défaut
        order.setPaymentStatus(PaymentStatus.PENDING); // Statut de paiement par défaut
        order.setPaymentAttempts(0); // Initialisation à 0
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());

        // Sauvegarder l'order dans la base
        orderRepository.insert(order);

        // Créer les OrderItems à partir des CartItems
        List<CartItem> cartItems = getCartItems(cart.getId());
        cartItems.forEach(cartItem -> {
            OrderItem orderItem = convertCartItemToOrderItem(order.getId(), cartItem);
            orderItemRepository.insert(orderItem);
            ProductPostResponse response = webClient.get()
                    .uri("/api/v1/product_post/{id}", cartItem.getProductId())
                    .retrieve()
                    .bodyToMono(ProductPostResponse.class)
                    .block();
                if (response.getQuantity() < cartItem.getQuantity()){
                    OrderToCartConverter otcc = new OrderToCartConverter(cartRepository, cartItemRepository, orderRepository, orderItemRepository, webClient);
                    otcc.convertOrderToCart(order);   //convert that order to cart item
                    //return o;
                    throw new ResourceNotFoundException("Insufficient stock for product ID " + cartItem.getProductId());
                }
                response.setQuantity(response.getQuantity() - cartItem.getQuantity());
                // transformer response en un ProductPostRequest

                ProductPostResponse response3 = webClient.put()
                        .uri("/api/v1/product_post/update/{id}", cartItem.getProductId())
                        .bodyValue(ProductConverter.convertToProductPostRequest(response))
                        .retrieve()
                        .bodyToMono(ProductPostResponse.class)
                        .block(); // BLOQUANT

    //            ProductPostRequest request = webClient
                // save the adjusted orderitem
                orderItem.setQuantity(cartItem.getQuantity());
        });

        // Supprimer le Cart et les CartItems après la création de l'Order
        cartRepository.delete(cart);
        cartItems.forEach(cartItem -> {
            cartItemRepository.delete(cartItem);
        });

        return order;
    }

    // Conversion de CartItem à OrderItem
    private OrderItem convertCartItemToOrderItem(UUID orderId, CartItem cartItem) {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderId(orderId);
        orderItem.setId(UUID.randomUUID()); // Un nouvel ID unique pour OrderItem
        orderItem.setProductId(cartItem.getProductId());
        orderItem.setQuantity(cartItem.getQuantity());
        orderItem.setUnitPrice(cartItem.getUnitPrice());
        orderItem.setSubtotal(cartItem.getUnitPrice().multiply(new BigDecimal(cartItem.getQuantity())));

        return orderItem;
    }

    // Récupérer les CartItems pour un Cart donné
    private List<CartItem> getCartItems(UUID cartId) {
        // Vous pouvez utiliser une méthode appropriée de votre template Cassandra pour récupérer les CartItems
        return cartItemRepository.findByCartId(cartId);
    }
}

