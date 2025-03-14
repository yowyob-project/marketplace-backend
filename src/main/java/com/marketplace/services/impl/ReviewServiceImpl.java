package com.marketplace.services.impl;

import com.marketplace.dtos.request.ReviewCreateRequestDTO;
import com.marketplace.dtos.response.ReviewResponseDTO;
import com.marketplace.entities.Review;
import com.marketplace.exceptions.ResourceNotFoundException;
import com.marketplace.repositories.ReviewRepository;
import com.marketplace.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    @Transactional
    public ReviewResponseDTO createReview(UUID userId, UUID productId, ReviewCreateRequestDTO request) {
        // Check if user has already reviewed this product
        reviewRepository.findByUserIdAndProductId(userId, productId)
                .ifPresent(r -> {
                    throw new IllegalStateException("User has already reviewed this product");
                });

        Review review = new Review();
        review.setId(UUID.randomUUID());
        review.setUserId(userId);
        review.setProductId(productId);
        review.setRating(request.getRating());
        review.setComment(request.getComment());
        review.setIsVerified(false);
        review.setCreatedAt(LocalDateTime.now());
        review.setUpdatedAt(LocalDateTime.now());

        review = reviewRepository.save(review);
        return convertToDTO(review);
    }

    private Review findReviewById(UUID reviewId) {
        return reviewRepository.findByReviewId(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found"));
    }

    @Override
    @Transactional
    public ReviewResponseDTO updateReview(UUID reviewId, ReviewCreateRequestDTO request) {
        Review review = findReviewById(reviewId);

        review.setRating(request.getRating());
        review.setComment(request.getComment());
        review.setUpdatedAt(LocalDateTime.now());
        // Reset verification status on update
        review.setIsVerified(false);

        review = reviewRepository.save(review);
        return convertToDTO(review);
    }

    @Override
    public ReviewResponseDTO getReview(UUID reviewId) {
        return convertToDTO(findReviewById(reviewId));
    }

    @Override
    public List<ReviewResponseDTO> getProductReviews(UUID productId) {
        return reviewRepository.findByProductId(productId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReviewResponseDTO> getUserReviews(UUID userId) {
        return reviewRepository.findByUserId(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteReview(UUID reviewId) {
        Review review = findReviewById(reviewId);
        reviewRepository.delete(review);
    }

    @Override
    @Transactional
    public ReviewResponseDTO verifyReview(UUID reviewId) {
        Review review = findReviewById(reviewId);
        review.setIsVerified(true);
        review = reviewRepository.save(review);
        return convertToDTO(review);
    }

//    private Review findReviewById(UUID reviewId) {
//        return reviewRepository.findById(reviewId)
//                .orElseThrow(() -> new ResourceNotFoundException("Review not found"));
//    }

    private ReviewResponseDTO convertToDTO(Review review) {
        ReviewResponseDTO dto = new ReviewResponseDTO();
        dto.setId(review.getId());
        dto.setUserId(review.getUserId());
        dto.setProductId(review.getProductId());
        dto.setRating(review.getRating());
        dto.setComment(review.getComment());
        dto.setIsVerified(review.getIsVerified());
        dto.setCreatedAt(review.getCreatedAt());
        dto.setUpdatedAt(review.getUpdatedAt());
        return dto;
    }
}