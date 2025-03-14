package com.marketplace.repositories;

import com.marketplace.entities.OrderItem;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface OrderItemRepository extends CassandraRepository<OrderItem, UUID> {
    List<OrderItem> findByOrderId(UUID orderId);
}