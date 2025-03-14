package com.marketplace.services.impl;

import com.marketplace.dtos.request.CartItemRequestDTO;
import com.marketplace.dtos.response.CartResponseDTO;
import com.marketplace.dtos.response.CartItemResponseDTO;
import com.marketplace.entities.Cart;
import com.marketplace.entities.CartItem;
import com.marketplace.exceptions.ResourceNotFoundException;
import com.marketplace.repositories.CartRepository;
import com.marketplace.repositories.CartItemRepository;
import com.marketplace.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public CartResponseDTO getCartByUserId(UUID userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user: " + userId));
        return convertToCartDTO(cart);
    }

    @Override
    public Cart getCartEntity(UUID userId) {
        return cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user: " + userId));
    }

    @Override
    public List<CartItem> getCartItems(UUID cartId) {
        return cartItemRepository.findByCartId(cartId);
    }

    @Override
    @Transactional
    public CartResponseDTO addItemToCart(UUID userId, CartItemRequestDTO itemDTO) {
        // Get or create cart
        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> createNewCart(userId));

        // Create cart item
        CartItem cartItem = new CartItem();
        cartItem.setId(UUID.randomUUID());
        cartItem.setCartId(cart.getId());
        cartItem.setProductId(itemDTO.getProductId());
        cartItem.setQuantity(itemDTO.getQuantity());
        cartItem.setUnitPrice(itemDTO.getUnitPrice());
        cartItem.setAddedAt(LocalDateTime.now());

        cartItemRepository.save(cartItem);

        // Update cart total
        updateCartTotal(cart);

        return convertToCartDTO(cart);
    }

    @Override
    @Transactional
    public void removeItemFromCart(UUID userId, UUID productId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user: " + userId));

        List<CartItem> items = cartItemRepository.findByCartId(cart.getId());
        items.stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst()
                .ifPresent(cartItemRepository::delete);

        updateCartTotal(cart);
    }

    @Override
    @Transactional
    public void clearCart(UUID userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user: " + userId));

        cartItemRepository.deleteByCartId(cart.getId());
        cart.setTotalAmount(BigDecimal.ZERO);
        cart.setUpdatedAt(LocalDateTime.now());
        cartRepository.save(cart);
    }

    private Cart createNewCart(UUID userId) {
        Cart cart = new Cart();
        cart.setId(UUID.randomUUID());
        cart.setUserId(userId);
        cart.setTotalAmount(BigDecimal.ZERO);
        cart.setCreatedAt(LocalDateTime.now());
        cart.setUpdatedAt(LocalDateTime.now());
        return cartRepository.save(cart);
    }

    private void updateCartTotal(Cart cart) {
        List<CartItem> items = cartItemRepository.findByCartId(cart.getId());
        BigDecimal total = items.stream()
                .map(item -> item.getUnitPrice().multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        cart.setTotalAmount(total);
        cart.setUpdatedAt(LocalDateTime.now());
        cartRepository.save(cart);
    }

    private CartResponseDTO convertToCartDTO(Cart cart) {
        List<CartItem> items = cartItemRepository.findByCartId(cart.getId());

        CartResponseDTO dto = new CartResponseDTO();
        dto.setId(cart.getId());
        dto.setUserId(cart.getUserId());
        dto.setTotalAmount(cart.getTotalAmount());
        dto.setCreatedAt(cart.getCreatedAt());
        dto.setUpdatedAt(cart.getUpdatedAt());
        dto.setItems(convertToCartItemDTOs(items));

        return dto;
    }

    private List<CartItemResponseDTO> convertToCartItemDTOs(List<CartItem> items) {
        return items.stream()
                .map(this::convertToCartItemDTO)
                .collect(Collectors.toList());
    }

    private CartItemResponseDTO convertToCartItemDTO(CartItem item) {
        CartItemResponseDTO dto = new CartItemResponseDTO();
        dto.setId(item.getId());
        dto.setProductId(item.getProductId());
        dto.setQuantity(item.getQuantity());
        dto.setUnitPrice(item.getUnitPrice());
        dto.setSubtotal(item.getUnitPrice().multiply(new BigDecimal(item.getQuantity())));
        dto.setAddedAt(item.getAddedAt());
        return dto;
    }
}