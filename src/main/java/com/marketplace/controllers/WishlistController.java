package com.marketplace.controllers;

import com.marketplace.dtos.request.WishlistItemRequestDTO;
import com.marketplace.dtos.response.WishlistResponseDTO;
import com.marketplace.services.WishlistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/api/wishlist")
@Tag(name = "Wishlist", description = "Wishlist management APIs")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @GetMapping
    @Operation(summary = "Get user's wishlist",
            description = "Retrieves or creates a wishlist for the user")
    @ApiResponse(responseCode = "200", description = "Wishlist retrieved successfully")
    public ResponseEntity<WishlistResponseDTO> getWishlist(
            @RequestHeader("User-Id") UUID userId) {
        return ResponseEntity.ok(wishlistService.getOrCreateWishlist(userId));
    }

    @PostMapping("/items")
    @Operation(summary = "Add item to wishlist",
            description = "Adds a product to the user's wishlist")
    @ApiResponse(responseCode = "200", description = "Item added successfully")
    public ResponseEntity<WishlistResponseDTO> addToWishlist(
            @RequestHeader("User-Id") UUID userId,
            @Valid @RequestBody WishlistItemRequestDTO request) {
        return ResponseEntity.ok(wishlistService.addToWishlist(userId, request.getProductId()));
    }

    @DeleteMapping("/items/{productId}")
    @Operation(summary = "Remove item from wishlist",
            description = "Removes a product from the user's wishlist")
    @ApiResponse(responseCode = "200", description = "Item removed successfully")
    public ResponseEntity<WishlistResponseDTO> removeFromWishlist(
            @RequestHeader("User-Id") UUID userId,
            @PathVariable UUID productId) {
        return ResponseEntity.ok(wishlistService.removeFromWishlist(userId, productId));
    }

    @GetMapping("/items/{productId}/check")
    @Operation(summary = "Check if product is in wishlist",
            description = "Checks if a specific product is in the user's wishlist")
    @ApiResponse(responseCode = "200", description = "Check completed successfully")
    public ResponseEntity<Boolean> isProductInWishlist(
            @RequestHeader("User-Id") UUID userId,
            @PathVariable UUID productId) {
        return ResponseEntity.ok(wishlistService.isProductInWishlist(userId, productId));
    }
}