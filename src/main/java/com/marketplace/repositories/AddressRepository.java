package com.marketplace.repositories;

import com.marketplace.entities.Address;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AddressRepository extends CassandraRepository<Address, UUID> {
    List<Address> findByUserId(UUID userId);
}