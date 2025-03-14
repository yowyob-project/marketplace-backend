package com.marketplace.invoice.entities;

import lombok.Data;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Table("invoice_items")
public class InvoiceItem {
    @PrimaryKey
    private UUID itemId;            // Identifiant unique de l'item

    private String invoiceId;       // Référence à la facture

    private String description;     // Description de l'item

    private int quantity;           // Quantité de l'item

    private BigDecimal unitPrice;   // Prix unitaire

    private BigDecimal total;       // Total (quantity * unitPrice)
}
