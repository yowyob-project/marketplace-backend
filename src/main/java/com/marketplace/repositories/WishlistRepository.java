package com.marketplace.repositories;

import com.marketplace.entities.Wishlist;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface WishlistRepository extends CassandraRepository<Wishlist, UUID> {
    Optional<Wishlist> findByUserId(UUID userId);
}