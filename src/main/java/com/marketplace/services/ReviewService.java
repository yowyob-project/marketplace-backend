package com.marketplace.services;

import com.marketplace.dtos.request.ReviewCreateRequestDTO;
import com.marketplace.dtos.response.ReviewResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface ReviewService {
    ReviewResponseDTO createReview(UUID userId, UUID productId, ReviewCreateRequestDTO request);
    ReviewResponseDTO updateReview(UUID reviewId, ReviewCreateRequestDTO request);
    ReviewResponseDTO getReview(UUID reviewId);
    List<ReviewResponseDTO> getProductReviews(UUID productId);
    List<ReviewResponseDTO> getUserReviews(UUID userId);
    void deleteReview(UUID reviewId);
    ReviewResponseDTO verifyReview(UUID reviewId);
}