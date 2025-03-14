package com.marketplace.controllers;

import com.marketplace.dtos.request.OrderStatusUpdateRequestDTO;
import com.marketplace.dtos.request.PaymentStatusUpdateRequestDTO;
import com.marketplace.dtos.response.OrderResponseDTO;
import com.marketplace.enums.OrderStatus;
import com.marketplace.services.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/api/orders")
@Tag(name = "Orders", description = "Order management APIs")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/checkout")
    @Operation(summary = "Create order from cart",
            description = "Creates a new order from the user's cart items")
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestHeader("User-Id") UUID userId) {
        return ResponseEntity.ok(orderService.createOrderFromCart(userId));
    }

    @GetMapping("/{orderId}")
    @Operation(summary = "Get order by ID")
    public ResponseEntity<OrderResponseDTO> getOrder(@PathVariable UUID orderId) {
        return ResponseEntity.ok(orderService.getOrder(orderId));
    }

    @GetMapping
    @Operation(summary = "Get user's orders")
    public ResponseEntity<List<OrderResponseDTO>> getUserOrders(@RequestHeader("User-Id") UUID userId) {
        return ResponseEntity.ok(orderService.getUserOrders(userId));
    }

    @PutMapping("/{orderId}/status")
    @Operation(summary = "Update order status")
    public ResponseEntity<OrderResponseDTO> updateOrderStatus(
            @PathVariable UUID orderId,
            @Valid @RequestBody OrderStatusUpdateRequestDTO request) {
        return ResponseEntity.ok(orderService.updateOrderStatus(orderId, request.getStatus()));
    }

    @PutMapping("/{orderId}/payment-status")
    @Operation(summary = "Update payment status",
            description = "Updates the payment status of an order")
    public ResponseEntity<OrderResponseDTO> updatePaymentStatus(
            @PathVariable UUID orderId,
            @Valid @RequestBody PaymentStatusUpdateRequestDTO request) {
        return ResponseEntity.ok(orderService.updatePaymentStatus(orderId, request.getStatus()));
    }

    @PostMapping("/{orderId}/cancel")
    @Operation(summary = "Cancel order")
    public ResponseEntity<Void> cancelOrder(@PathVariable UUID orderId) {
        orderService.cancelOrder(orderId);
        return ResponseEntity.ok().build();
    }
}