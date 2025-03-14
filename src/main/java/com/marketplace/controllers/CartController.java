package com.marketplace.controllers;

import com.marketplace.dtos.request.CartItemRequestDTO;
import com.marketplace.dtos.response.CartResponseDTO;
import com.marketplace.services.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/api/cart")
@Tag(name = "Cart", description = "Cart management APIs")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping
    @Operation(summary = "Get user's cart",
            description = "Retrieves the cart and its items for a specific user")
    @ApiResponse(responseCode = "200", description = "Cart retrieved successfully",
            content = @Content(schema = @Schema(implementation = CartResponseDTO.class)))
    @ApiResponse(responseCode = "404", description = "Cart not found")
    public ResponseEntity<CartResponseDTO> getCart(@RequestHeader("User-Id") UUID userId) {
        return ResponseEntity.ok(cartService.getCartByUserId(userId));
    }

    @PostMapping("/items")
    @Operation(summary = "Add item to cart",
            description = "Adds a new item to the user's cart or updates quantity if item exists")
    @ApiResponse(responseCode = "200", description = "Item added to cart successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request")
    public ResponseEntity<CartResponseDTO> addToCart(
            @RequestHeader("User-Id") UUID userId,
            @Valid @RequestBody CartItemRequestDTO itemDTO) {
        return ResponseEntity.ok(cartService.addItemToCart(userId, itemDTO));
    }

    @DeleteMapping("/items/{productId}")
    @Operation(summary = "Remove item from cart",
            description = "Removes a specific item from the user's cart")
    @ApiResponse(responseCode = "200", description = "Item removed successfully")
    @ApiResponse(responseCode = "404", description = "Item not found")
    public ResponseEntity<Void> removeFromCart(
            @RequestHeader("User-Id") UUID userId,
            @PathVariable UUID productId) {
        cartService.removeItemFromCart(userId, productId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/clear")
    @Operation(summary = "Clear cart",
            description = "Removes all items from the user's cart")
    @ApiResponse(responseCode = "200", description = "Cart cleared successfully")
    @ApiResponse(responseCode = "404", description = "Cart not found")
    public ResponseEntity<Void> clearCart(@RequestHeader("User-Id") UUID userId) {
        cartService.clearCart(userId);
        return ResponseEntity.ok().build();
    }
}