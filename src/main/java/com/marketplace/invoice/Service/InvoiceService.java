package com.marketplace.invoice.Service;

import com.marketplace.invoice.entities.Invoice;
import com.marketplace.invoice.entities.InvoiceItem;
import com.marketplace.invoice.repositories.InvoiceItemRepository;
import com.marketplace.invoice.repositories.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private InvoiceItemRepository invoiceItemRepository;

    // Créer une nouvelle facture
    public Invoice createInvoice(String customerId, BigDecimal amount, LocalDate dueDate) {
        Invoice invoice = new Invoice();
        invoice.setInvoiceId(generateInvoiceId());
        invoice.setCustomerId(customerId);
        invoice.setAmount(amount);
        invoice.setIssueDate(LocalDate.now());
        invoice.setDueDate(dueDate);
        invoice.setStatus("PENDING"); // Statut initial

        return invoiceRepository.save(invoice);
    }

    // Récupérer toutes les factures d'un client
    public List<Invoice> getInvoicesByCustomer(String customerId) {
        return invoiceRepository.findByCustomerId(customerId);
    }

    // Récupérer une facture par son ID
    public Optional<Invoice> getInvoiceById(String invoiceId) {
        return invoiceRepository.findById(invoiceId);
    }

    // Mettre à jour le statut d'une facture
    public Invoice updateInvoiceStatus(String invoiceId, String newStatus) {
        Optional<Invoice> invoiceOpt = getInvoiceById(invoiceId);
        if (invoiceOpt.isPresent()) {
            Invoice invoice = invoiceOpt.get();
            invoice.setStatus(newStatus);
            return invoiceRepository.save(invoice);
        }
        return null;
    }

    // Générer un identifiant unique pour la facture
    private String generateInvoiceId() {
        return "INV-" + System.currentTimeMillis();
    }

    public InvoiceItem addInvoiceItem(InvoiceItem item) {
        return invoiceItemRepository.save(item);
    }

    public List<InvoiceItem> getItemsByInvoiceId(String invoiceId) {
        return invoiceItemRepository.findByInvoiceId(invoiceId);
    }
}