package com.marketplace.invoice.repositories;

import com.marketplace.invoice.entities.Invoice;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends CassandraRepository <Invoice, String> {
    List<Invoice> findByCustomerId(String customerId);
}
