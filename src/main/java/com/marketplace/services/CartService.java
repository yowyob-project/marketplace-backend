//package com.marketplace.services;
//
//import com.marketplace.dtos.AddToCartDTO;
//import com.marketplace.dtos.CartDTO;
//import com.marketplace.dtos.CartItemDTO;
//import com.marketplace.entities.Cart;
//import com.marketplace.entities.CartItem;
//import com.marketplace.entities.Product;
//import com.marketplace.exceptions.ResourceNotFoundException;
//import com.marketplace.repositories.CartRepository;
//import com.marketplace.repositories.CartItemRepository;
//import com.marketplace.repositories.ProductRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.UUID;
//import java.util.stream.Collectors;
//
//@Service
//public class CartService {
//
//    @Autowired
//    private CartRepository cartRepository;
//
//    @Autowired
//    private CartItemRepository cartItemRepository;
//
//    @Autowired
//    private ProductRepository productRepository;
//
//    @Transactional
//    public CartDTO addToCart(UUID userId, AddToCartDTO addToCartDTO) {
//        // Get or create cart for user
//        Cart cart = cartRepository.findByUserId(userId)
//                .orElseGet(() -> createNewCart(userId));
//
//        // Get product
//        Product product = productRepository.findById(addToCartDTO.getProductId())
//                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
//
//        // Create or update cart item
//        CartItem cartItem = new CartItem();
//        cartItem.setId(UUID.randomUUID());
//        cartItem.setCartId(cart.getId());
//        cartItem.setProductId(product.getId());
//        cartItem.setQuantity(addToCartDTO.getQuantity());
//        cartItem.setUnitPrice(product.getPrice());
//        cartItem.setAddedAt(LocalDateTime.now());
//
//        cartItemRepository.save(cartItem);
//
//        // Update cart total
//        updateCartTotal(cart);
//
//        return getCartDTO(cart);
//    }
//
//    public CartDTO getCart(UUID userId) {
//        Cart cart = cartRepository.findByUserId(userId)
//                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
//        return getCartDTO(cart);
//    }
//
//    @Transactional
//    public void removeFromCart(UUID userId, UUID productId) {
//        Cart cart = cartRepository.findByUserId(userId)
//                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
//
//        List<CartItem> items = cartItemRepository.findByCartId(cart.getId());
//        items.stream()
//                .filter(item -> item.getProductId().equals(productId))
//                .findFirst()
//                .ifPresent(cartItemRepository::delete);
//
//        updateCartTotal(cart);
//    }
//
//    @Transactional
//    public void clearCart(UUID userId) {
//        Cart cart = cartRepository.findByUserId(userId)
//                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
//
//        cartItemRepository.deleteByCartId(cart.getId());
//        cart.setTotalAmount(BigDecimal.ZERO);
//        cart.setUpdatedAt(LocalDateTime.now());
//        cartRepository.save(cart);
//    }
//
//    private Cart createNewCart(UUID userId) {
//        Cart cart = new Cart();
//        cart.setId(UUID.randomUUID());
//        cart.setUserId(userId);
//        cart.setTotalAmount(BigDecimal.ZERO);
//        cart.setCreatedAt(LocalDateTime.now());
//        cart.setUpdatedAt(LocalDateTime.now());
//        return cartRepository.save(cart);
//    }
//
//    private void updateCartTotal(Cart cart) {
//        List<CartItem> items = cartItemRepository.findByCartId(cart.getId());
//        BigDecimal total = items.stream()
//                .map(item -> item.getUnitPrice().multiply(new BigDecimal(item.getQuantity())))
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
//
//        cart.setTotalAmount(total);
//        cart.setUpdatedAt(LocalDateTime.now());
//        cartRepository.save(cart);
//    }
//
//    private CartDTO getCartDTO(Cart cart) {
//        List<CartItem> items = cartItemRepository.findByCartId(cart.getId());
//        List<CartItemDTO> itemDTOs = items.stream()
//                .map(this::convertToCartItemDTO)
//                .collect(Collectors.toList());
//
//        CartDTO cartDTO = new CartDTO();
//        cartDTO.setId(cart.getId());
//        cartDTO.setItems(itemDTOs);
//        cartDTO.setTotalAmount(cart.getTotalAmount());
//        cartDTO.setTotalItems(items.size());
//
//        return cartDTO;
//    }
//
//    private CartItemDTO convertToCartItemDTO(CartItem cartItem) {
//        Product product = productRepository.findById(cartItem.getProductId())
//                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
//
//        CartItemDTO dto = new CartItemDTO();
//        dto.setId(cartItem.getId());
//        dto.setProductId(product.getId());
//        dto.setProductName(product.getName());
//        dto.setQuantity(cartItem.getQuantity());
//        dto.setUnitPrice(cartItem.getUnitPrice());
//        dto.setSubtotal(cartItem.getUnitPrice().multiply(new BigDecimal(cartItem.getQuantity())));
//        dto.setAddedAt(cartItem.getAddedAt());
//
//        return dto;
//    }
//
//    // Add these new methods
//    public Cart getCartEntity(UUID userId) {
//        return cartRepository.findByUserId(userId)
//                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user: " + userId));
//    }
//
//    public List<CartItem> getCartItems(UUID cartId) {
//        return cartItemRepository.findByCartId(cartId);
//    }
//
//}



package com.marketplace.services;

import com.marketplace.dtos.request.CartItemRequestDTO;
import com.marketplace.dtos.response.CartResponseDTO;
import com.marketplace.entities.Cart;
import com.marketplace.entities.CartItem;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.UUID;

@Service
public interface CartService {
    CartResponseDTO getCartByUserId(UUID userId);
    CartResponseDTO addItemToCart(UUID userId, CartItemRequestDTO itemDTO);
    void removeItemFromCart(UUID userId, UUID productId);
    void clearCart(UUID userId);

    Cart getCartEntity(UUID userId);
    List<CartItem> getCartItems(UUID cartId);
}