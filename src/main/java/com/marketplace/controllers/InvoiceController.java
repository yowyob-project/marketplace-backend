package com.marketplace.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import com.marketplace.models.Product;
import com.marketplace.services.impl.InvoiceServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@Tag(name = "Invoice", description = "Invoice management APIs")
@RequestMapping("/api/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceServiceImpl invoiceService;

    @Operation(
            summary = "Générer une facture PDF pour un client",
            description = "Génère une facture PDF à partir des produits pour un client donné et l'envoie dans la réponse HTTP."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Facture générée avec succès",
                    content = @Content(mediaType = "application/pdf", schema = @Schema(type = "string", format = "binary"))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Requête invalide (ex. nom client manquant ou format des produits incorrect)",
                    content = @Content
            )
    })
    @PostMapping("/generate")
    public void generateInvoice(@RequestParam String customerName,
                                @RequestBody List<Product> products,
                                HttpServletResponse response) throws Exception {
        invoiceService.generateInvoice(customerName, products, response);
    }
}
