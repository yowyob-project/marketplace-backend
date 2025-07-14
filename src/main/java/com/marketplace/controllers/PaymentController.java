package com.marketplace.controllers;

import com.marketplace.dtos.response.CartResponseDTO;
import com.marketplace.dtos.response.OrderResponseDTO;
import com.marketplace.entities.Order;
import com.marketplace.enums.PaymentStatus;
import com.marketplace.models.*;
import com.marketplace.repositories.OrderRepository;
import com.marketplace.services.CartService;
import com.marketplace.services.OrderService;
import com.marketplace.services.impl.OrderToCartConverter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/api/payment-client")
@Tag(name = "Payment", description = "Payment management APIs")
public class PaymentController {

    private final WebClient paymentServiceWebClient;

    private final OrderRepository orderRepository;

    public PaymentController(WebClient paymentServiceWebClient, OrderRepository orderRepository) {
        this.paymentServiceWebClient = paymentServiceWebClient;
        this.orderRepository = orderRepository;
    }

    // Demander un paiement
    @PostMapping("/payin")
    @Operation(summary = "Payin an order",
            description = "Pay your bill")
    @ApiResponse(responseCode = "200", description = "Pay order started")
    public ResponseEntity<PaymentResponse> createPaymentRequest(
            @RequestBody PaymentRequest request) {
        try {
            //.uri("/3qUkt_O1YcClmouM6_-W-dZ5bWdx13uf/payin")
            PaymentResponse response = paymentServiceWebClient.post()
                    .uri("/payin")
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(PaymentResponse.class)
                    .block(); // BLOQUANT

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    private CartService cartService;
    private OrderService orderService;
    private OrderToCartConverter orderToCartConverter;
    @PostMapping("/payin/{userId}/{orderId}/{userNumber}")
    @Operation(summary = "Payin an order for a specific user",
            description = "Pay your bill")
    @ApiResponse(responseCode = "200", description = "Pay order started")
    public ResponseEntity<PaymentResponse> createPaymentRequestByUserId(
            @RequestBody PaymentRequest request, @PathVariable String UserId, @PathVariable UUID orderId, @PathVariable String userNumber) {
        try {
            CartResponseDTO userCart = cartService.getCartByUserId(UUID.fromString(UserId));

            double totalAmount = userCart.getTotalAmount().doubleValue();

            PaymentRequest paymentRequest = PaymentRequest.builder()
                    .transactionAmount(totalAmount)
                    .payerReference(UserId)
                    .payerPhoneNumber(userNumber)
                    .build();

            PaymentResponse response = paymentServiceWebClient.post()
                    .uri("/3qUkt_O1YcClmouM6_-W-dZ5bWdx13uf/payin")
                    .bodyValue(paymentRequest)
                    .retrieve()
                    .bodyToMono(PaymentResponse.class)
                    .block(); // BLOQUANT

            if (response.getStatus() == "SUCCESS"){
                System.out.println("Payment Successful");

                OrderResponseDTO orderDTO = orderService.createOrderFromCart(UUID.fromString(UserId));
                Order order = convertToOrder(orderDTO);
                orderRepository.save(order);
            }
            else{
                //Cart cart = orderToCartConverter.convertOrderToCart(order);
                System.out.println("Payment failed, order converted back to cart.");
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
//            log.error("Payment failed for user {}: {}", UserId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    private Order convertToOrder(OrderResponseDTO dto) {
        return Order.builder()
                .id(dto.getId())
                .userId(dto.getUserId())
                .totalAmount(dto.getTotalAmount())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .build();
    }

    // Vérifier le statut du paiement
    @GetMapping("/transactions/{transaction_code}/status")
    @Operation(summary = "Get payment status of an order",
            description = "Get the payment status of your bill")
    @ApiResponse(responseCode = "200", description = "Get the payment status")
    public ResponseEntity<PaymentStatusResponse> getPaymentStatus(
            @PathVariable String transaction_code) {
        try {
            PaymentStatusRequest statusRequest = new PaymentStatusRequest();
            statusRequest.setTransactionCode(transaction_code);

            PaymentStatusResponse response = paymentServiceWebClient.get()
                    .uri("/3qUkt_O1YcClmouM6_-W-dZ5bWdx13uf/transactions/{transaction_code}/status", transaction_code)
                    .retrieve()
                    .bodyToMono(PaymentStatusResponse.class)
                    .block(); // BLOQUANT

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/callback")
    @Operation(summary = "Payin an order for a specific user callback path",
            description = "Pay your bill")
    @ApiResponse(responseCode = "200", description = "Payment Update for a specific order ")
    public void callbback(
            @RequestBody PaymentResponseCallback request) {
        Order order = orderService.getOrderByPaymentReference(request.getTransactionReference());
        order.setStatus(request.getTransactionStatus());
        orderRepository.save(order);
    }
}

