package com.marketplace.models;

import java.time.LocalDateTime;
import java.util.UUID;

public class ProductConverter {
    public static ProductPostRequest convertToProductPostRequest(ProductPostResponse response) {
        ProductPostRequest request = new ProductPostRequest();

        // Copie des propriétés
        request.setMarchandId(response.getMarchandId());
        request.setVariationId(response.getVariationId());
        request.setCategorieId(response.getCategorieId());
        request.setName(response.getName());
        request.setLongDescription(response.getLongDescription());
        request.setShortDescription(response.getShortDescription());
        request.setLifespan(response.getLifespan());
        request.setQuantity(response.getQuantity());
        request.setSaleUnit(response.getSaleUnit());
        request.setBasePrice(response.getBasePrice());
        request.setWeight(response.getWeight());
        request.setDefaultCurrency(response.getDefaultCurrency());
        request.setNextAvailableTime(response.getNextAvailableTime());
        request.setStatus(response.getStatus());
        request.setExpiresAt(response.getExpiresAt());

        return request;
    }

    public static ProductPostResponse convertToProductPostResponse(ProductPostRequest request) {
        ProductPostResponse response = new ProductPostResponse();

        // Copie des propriétés de ProductPostRequest vers ProductPostResponse
        response.setMarchandId(request.getMarchandId());
        response.setVariationId(request.getVariationId());
        response.setCategorieId(request.getCategorieId());
        response.setName(request.getName());
        response.setLongDescription(request.getLongDescription());
        response.setShortDescription(request.getShortDescription());
        response.setLifespan(request.getLifespan());
        response.setQuantity(request.getQuantity());
        response.setSaleUnit(request.getSaleUnit());
        response.setBasePrice(request.getBasePrice());
        response.setWeight(request.getWeight());
        response.setDefaultCurrency(request.getDefaultCurrency());
        response.setNextAvailableTime(request.getNextAvailableTime());
        response.setStatus(request.getStatus());
        response.setExpiresAt(request.getExpiresAt());

        // Ajouter les autres propriétés manquantes si nécessaire, comme "id", "createdAt", etc.
        // Par exemple, si vous souhaitez définir un ID aléatoire pour la réponse :
        response.setId(UUID.randomUUID());
        response.setCreatedAt(LocalDateTime.now());
        response.setUpdatedAt(LocalDateTime.now());

        return response;
    }
}
