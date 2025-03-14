package com.marketplace.invoice.repositories;

import com.marketplace.invoice.entities.InvoiceItem;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface InvoiceItemRepository extends CassandraRepository<InvoiceItem, UUID> {
    List<InvoiceItem> findByInvoiceId(String invoiceId);
}
