package com.marketplace.services.impl;

import com.marketplace.dtos.response.OrderResponseDTO;
import com.marketplace.entities.Cart;
import com.marketplace.entities.CartItem;
import com.marketplace.entities.Order;
import com.marketplace.entities.OrderItem;
import com.marketplace.models.ProductConverter;
import com.marketplace.models.ProductPostResponse;
import com.marketplace.repositories.CartItemRepository;
import com.marketplace.repositories.CartRepository;
import com.marketplace.repositories.OrderItemRepository;
import com.marketplace.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class OrderToCartConverter {

    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    private final WebClient webClient;

    @Autowired
    public OrderToCartConverter(CartRepository cartRepository, CartItemRepository cartItemRepository, OrderRepository orderRepository, OrderItemRepository orderItemRepository, WebClient webClient) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.webClient = webClient;
    }

    // Conversion d'un Order en Cart
    public Cart convertOrderToCart(Order order) {
//    public Cart convertOrderToCart(OrderResponseDTO order) {
        // Créer un Cart à partir de l'Order
        Cart cart = new Cart();
        cart.setId(UUID.randomUUID()); // Crée un nouvel UUID pour le Cart
        cart.setUserId(order.getUserId());
        cart.setTotalAmount(order.getTotalAmount()); // Récupère le total de l'Order
        cart.setCreatedAt(LocalDateTime.now()); // On peut ajuster si nécessaire
        cart.setUpdatedAt(LocalDateTime.now()); // On peut ajuster si nécessaire

        // Sauvegarder le Cart dans la base
        cartRepository.insert(cart);

        // Créer les CartItems à partir des OrderItems
        List<OrderItem> orderItems = getOrderItems(order.getId());
        orderItems.forEach(orderItem -> {
            CartItem cartItem = convertOrderItemToCartItem(cart.getId(), orderItem);
            cartItemRepository.insert(cartItem);
            ProductPostResponse response = webClient.get()
                    .uri("/api/v1/product_post/{id}", orderItem.getProductId())
                    .retrieve()
                    .bodyToMono(ProductPostResponse.class)
                    .block();
            response.setQuantity(response.getQuantity() + cartItem.getQuantity());
            // transformer response en un ProductPostRequest

            ProductPostResponse response3 = webClient.put()
                    .uri("/api/v1/product_post/update/{id}", cartItem.getProductId())
                    .bodyValue(ProductConverter.convertToProductPostRequest(response))
                    .retrieve()
                    .bodyToMono(ProductPostResponse.class)
                    .block(); // BLOQUANT
        });

        // Supprimer l'Order et ses OrderItems après la conversion en Cart
        // orderRepository.delete(order);
        // orderItems.forEach(orderItem -> {
        //    orderItemRepository.delete(orderItem);
        // });

        return cart;
    }

    // Conversion d'un OrderItem en CartItem
    private CartItem convertOrderItemToCartItem(UUID cartId, OrderItem orderItem) {
        CartItem cartItem = new CartItem();
        cartItem.setCartId(cartId);
        cartItem.setId(UUID.randomUUID()); // Un nouvel ID unique pour CartItem
        cartItem.setProductId(orderItem.getProductId());
        cartItem.setQuantity(orderItem.getQuantity());
        cartItem.setUnitPrice(orderItem.getUnitPrice());
        cartItem.setAddedAt(LocalDateTime.now()); // On peut ajuster si nécessaire

        return cartItem;
    }

    // Récupérer les OrderItems pour un Order donné
    private List<OrderItem> getOrderItems(UUID orderId) {
        // Vous pouvez utiliser une méthode appropriée de votre template Cassandra pour récupérer les OrderItems
        return orderItemRepository.findByOrderId(orderId);
    }
}

