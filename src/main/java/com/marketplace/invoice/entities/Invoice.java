package com.marketplace.invoice.entities;

import lombok.Data;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Table("invoices")
public class Invoice {
    @PrimaryKey
    private String invoiceId; // Identifiant unique de la facture

    private String customerId; // Identifiant du client

    private BigDecimal amount; // Montant total de la facture

    private LocalDate issueDate; // Date d'émission de la facture

    private LocalDate dueDate; // Date d'échéance

    private String status; // Statut de la facture (ex : "PAID", "PENDING")
}
