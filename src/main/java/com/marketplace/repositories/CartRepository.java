package com.marketplace.repositories;

import com.marketplace.entities.Cart;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartRepository extends CassandraRepository<Cart, UUID> {
    Optional<Cart> findByUserId(UUID userId);
}