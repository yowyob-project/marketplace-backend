package com.marketplace.services.impl;

import com.marketplace.dtos.response.WishlistResponseDTO;
import com.marketplace.dtos.response.WishlistItemResponseDTO;
import com.marketplace.entities.Wishlist;
import com.marketplace.entities.WishlistItem;
import com.marketplace.exceptions.ResourceNotFoundException;
import com.marketplace.repositories.WishlistRepository;
import com.marketplace.repositories.WishlistItemRepository;
import com.marketplace.services.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class WishlistServiceImpl implements WishlistService {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private WishlistItemRepository wishlistItemRepository;

    @Override
    public WishlistResponseDTO getOrCreateWishlist(UUID userId) {
        Wishlist wishlist = wishlistRepository.findByUserId(userId)
                .orElseGet(() -> createWishlist(userId));
        return convertToDTO(wishlist);
    }

    @Override
    @Transactional
    public WishlistResponseDTO addToWishlist(UUID userId, UUID productId) {
        Wishlist wishlist = wishlistRepository.findByUserId(userId)
                .orElseGet(() -> createWishlist(userId));

        // Check if product already exists in wishlist
        if (!isProductInWishlist(userId, productId)) {
            WishlistItem item = new WishlistItem();
            item.setId(UUID.randomUUID());
            item.setWishlistId(wishlist.getId());
            item.setProductId(productId);
            item.setAddedAt(LocalDateTime.now());
            wishlistItemRepository.save(item);
        }

        return convertToDTO(wishlist);
    }

    @Override
    @Transactional
    public WishlistResponseDTO removeFromWishlist(UUID userId, UUID productId) {
        Wishlist wishlist = wishlistRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Wishlist not found"));

        wishlistItemRepository.findByWishlistIdAndProductId(wishlist.getId(), productId)
                .ifPresent(wishlistItemRepository::delete);

        return convertToDTO(wishlist);
    }

    @Override
    public boolean isProductInWishlist(UUID userId, UUID productId) {
        Wishlist wishlist = wishlistRepository.findByUserId(userId)
                .orElse(null);

        if (wishlist == null) {
            return false;
        }

        return wishlistItemRepository.findByWishlistIdAndProductId(wishlist.getId(), productId)
                .isPresent();
    }

    private Wishlist createWishlist(UUID userId) {
        Wishlist wishlist = new Wishlist();
        wishlist.setId(UUID.randomUUID());
        wishlist.setUserId(userId);
        wishlist.setCreatedAt(LocalDateTime.now());
        return wishlistRepository.save(wishlist);
    }

    private WishlistResponseDTO convertToDTO(Wishlist wishlist) {
        List<WishlistItem> items = wishlistItemRepository.findByWishlistId(wishlist.getId());

        WishlistResponseDTO dto = new WishlistResponseDTO();
        dto.setId(wishlist.getId());
        dto.setUserId(wishlist.getUserId());
        dto.setCreatedAt(wishlist.getCreatedAt());
        dto.setItems(convertToItemDTOs(items));

        return dto;
    }

    private List<WishlistItemResponseDTO> convertToItemDTOs(List<WishlistItem> items) {
        return items.stream()
                .map(this::convertToItemDTO)
                .collect(Collectors.toList());
    }

    private WishlistItemResponseDTO convertToItemDTO(WishlistItem item) {
        WishlistItemResponseDTO dto = new WishlistItemResponseDTO();
        dto.setId(item.getId());
        dto.setProductId(item.getProductId());
        dto.setAddedAt(item.getAddedAt());
        return dto;
    }
}