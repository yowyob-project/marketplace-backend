package com.marketplace.repositories;

import com.marketplace.entities.Order;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends CassandraRepository<Order, UUID> {
//    List<Order> findByUserId(UUID userId);

    @Query("SELECT * FROM orders WHERE user_id = ?0 ALLOW FILTERING")
    List<Order> findByUserId(UUID userId);

    @Query("SELECT * FROM orders WHERE organisation_id = ?0 ALLOW FILTERING")
    List<Order> findByOrganisationIdAndStatus(UUID organisationId, String status);

    @Query("SELECT * FROM orders WHERE payment_reference = ?0")
    Order findByPaymentReference(String payment_Reference);
}


