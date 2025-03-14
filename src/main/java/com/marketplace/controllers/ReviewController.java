package com.marketplace.controllers;

import com.marketplace.dtos.request.ReviewCreateRequestDTO;
import com.marketplace.dtos.response.ReviewResponseDTO;
import com.marketplace.services.ReviewService;
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
@RequestMapping("/api/reviews")
@Tag(name = "Reviews", description = "Review management APIs")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/products/{productId}")
    @Operation(summary = "Create review",
            description = "Creates a new review for a product")
    @ApiResponse(responseCode = "200", description = "Review created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    public ResponseEntity<ReviewResponseDTO> createReview(
            @RequestHeader("User-Id") UUID userId,
            @PathVariable UUID productId,
            @Valid @RequestBody ReviewCreateRequestDTO request) {
        return ResponseEntity.ok(reviewService.createReview(userId, productId, request));
    }

    @GetMapping("/{reviewId}")
    @Operation(summary = "Get review by ID",
            description = "Retrieves a specific review by its ID")
    @ApiResponse(responseCode = "200", description = "Review retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Review not found")
    public ResponseEntity<ReviewResponseDTO> getReview(
            @PathVariable UUID reviewId) {
        return ResponseEntity.ok(reviewService.getReview(reviewId));
    }

    @GetMapping("/products/{productId}")
    @Operation(summary = "Get product reviews",
            description = "Retrieves all reviews for a specific product")
    @ApiResponse(responseCode = "200", description = "Reviews retrieved successfully")
    public ResponseEntity<List<ReviewResponseDTO>> getProductReviews(
            @PathVariable UUID productId) {
        return ResponseEntity.ok(reviewService.getProductReviews(productId));
    }

    @GetMapping("/users/{userId}")
    @Operation(summary = "Get user reviews",
            description = "Retrieves all reviews by a specific user")
    @ApiResponse(responseCode = "200", description = "Reviews retrieved successfully")
    public ResponseEntity<List<ReviewResponseDTO>> getUserReviews(
            @PathVariable UUID userId) {
        return ResponseEntity.ok(reviewService.getUserReviews(userId));
    }

    @PutMapping("/{reviewId}")
    @Operation(summary = "Update review",
            description = "Updates an existing review")
    @ApiResponse(responseCode = "200", description = "Review updated successfully")
    @ApiResponse(responseCode = "404", description = "Review not found")
    public ResponseEntity<ReviewResponseDTO> updateReview(
            @PathVariable UUID reviewId,
            @Valid @RequestBody ReviewCreateRequestDTO request) {
        return ResponseEntity.ok(reviewService.updateReview(reviewId, request));
    }

    @DeleteMapping("/{reviewId}")
    @Operation(summary = "Delete review",
            description = "Deletes a review")
    @ApiResponse(responseCode = "200", description = "Review deleted successfully")
    @ApiResponse(responseCode = "404", description = "Review not found")
    public ResponseEntity<Void> deleteReview(
            @PathVariable UUID reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{reviewId}/verify")
    @Operation(summary = "Verify review",
            description = "Marks a review as verified")
    @ApiResponse(responseCode = "200", description = "Review verified successfully")
    @ApiResponse(responseCode = "404", description = "Review not found")
    public ResponseEntity<ReviewResponseDTO> verifyReview(
            @PathVariable UUID reviewId) {
        return ResponseEntity.ok(reviewService.verifyReview(reviewId));
    }
}