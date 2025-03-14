package com.marketplace.invoice.controllers;

import com.marketplace.invoice.Service.InvoiceService;
import com.marketplace.invoice.entities.Invoice;
import com.marketplace.invoice.entities.InvoiceItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceControllerr {

    @Autowired
    private InvoiceService invoiceService;

    // Créer une nouvelle facture
    @PostMapping
    public Invoice createInvoice(@RequestParam String customerId,
                                 @RequestParam BigDecimal amount,
                                 @RequestParam LocalDate dueDate) {
        return invoiceService.createInvoice(customerId, amount, dueDate);
    }

    // Obtenir les factures d'un client
    @GetMapping("/customer/{customerId}")
    public List<Invoice> getInvoicesByCustomer(@PathVariable String customerId) {
        return invoiceService.getInvoicesByCustomer(customerId);
    }

    // Obtenir une facture par ID
    @GetMapping("/{invoiceId}")
    public Optional<Invoice> getInvoiceById(@PathVariable String invoiceId) {
        return invoiceService.getInvoiceById(invoiceId);
    }

    // Mettre à jour le statut d'une facture
    @PutMapping("/{invoiceId}/status")
    public Invoice updateInvoiceStatus(@PathVariable String invoiceId,
                                       @RequestParam String newStatus) {
        return invoiceService.updateInvoiceStatus(invoiceId, newStatus);
    }

    @PostMapping("/{id}/items")
    public InvoiceItem addItem(@PathVariable("id") String invoiceId, @RequestBody InvoiceItem item) {
        item.setInvoiceId(invoiceId);
        item.setItemId(UUID.randomUUID());
        return invoiceService.addInvoiceItem(item);
    }

    @GetMapping("/{id}/items")
    public List<InvoiceItem> getItems(@PathVariable("id") String invoiceId) {
        return invoiceService.getItemsByInvoiceId(invoiceId);
    }
}