package com.marketplace.services;

import com.marketplace.dtos.response.WishlistResponseDTO;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface WishlistService {
    WishlistResponseDTO getOrCreateWishlist(UUID userId);
    WishlistResponseDTO addToWishlist(UUID userId, UUID productId);
    WishlistResponseDTO removeFromWishlist(UUID userId, UUID productId);
    boolean isProductInWishlist(UUID userId, UUID productId);
}