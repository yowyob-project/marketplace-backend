package com.marketplace.repositories;

import com.marketplace.entities.WishlistItem;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface WishlistItemRepository extends CassandraRepository<WishlistItem, UUID> {
    List<WishlistItem> findByWishlistId(UUID wishlistId);
    Optional<WishlistItem> findByWishlistIdAndProductId(UUID wishlistId, UUID productId);
}