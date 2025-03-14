package com.marketplace.services.impl;

import com.marketplace.models.Product;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import java.util.List;


@Service
public class InvoiceServiceImpl {

    @Value("${companyName}")
    private String companyName;

    @Value("${serviceName}")
    private String serviceName;

    public void generateInvoice(String customerName, List<Product> products, HttpServletResponse response) throws Exception {
        // Créer un document PDF avec une taille de page A4
        Document document = new Document(PageSize.A4);

        // Configuration de la réponse HTTP pour le PDF
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=invoice.pdf");

        // Créer un PdfWriter pour générer le PDF
        PdfWriter.getInstance(document, response.getOutputStream());

        // Ouvrir le document PDF pour l'édition
        document.open();

        // Définir la police pour les titres et le contenu
        Font titleFont = new Font(Font.HELVETICA, 16, Font.BOLD);
        Font contentFont = new Font(Font.HELVETICA, 12, Font.NORMAL);

        // Ajouter le titre de la facture
        document.add(new Paragraph("Facture", titleFont));

        // Ajouter les informations de la société et du service
        document.add(new Paragraph("Société: " + companyName, contentFont));
        document.add(new Paragraph("Service: " + serviceName, contentFont));

        // Ajouter le nom du client
        document.add(new Paragraph("Client: " + customerName, contentFont));

        // Ajouter la liste des produits avec leurs prix
        document.add(new Paragraph("Produits:", titleFont));
        double totalAmount = 0;
        for (Product product : products) {
            document.add(new Paragraph(product.getName() + " - " + product.getPrice() + "€", contentFont));
            totalAmount += product.getPrice();
        }

        // Ajouter le montant total
        document.add(new Paragraph("Montant Total: " + totalAmount + "€", contentFont));

        // Fermer le document PDF
        document.close();
    }
}

