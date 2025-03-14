package com.marketplace.models;

import java.util.UUID;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Builder
public class RessourceRequest {
    @JsonProperty("productBrandId")
    private UUID productBrandId;

    @JsonProperty("immatriculation")
    private String immatriculation;

    @JsonProperty("serialNumber")
    private String serialNumber;

    @JsonProperty("skuCode")
    private String skuCode;

    @JsonProperty("barCode")
    private String barCode;

    @JsonProperty("qrCode")
    private String qrCode;

    @JsonProperty("name")
    private String name;

    @JsonProperty("shortDescription")
    private String shortDescription;

    @JsonProperty("longDescription")
    private String longDescription;

    @JsonProperty("storageCondition")
    private String storageCondition;

    @JsonProperty("modelId")
    private UUID modelId;

    @JsonProperty("iotNumber")
    private String iotNumber;

    @JsonProperty("availableQuantity")
    private int availableQuantity;

    @JsonProperty("productType")
    private String productType;

    @JsonProperty("basePrice")
    private double basePrice;

    @JsonProperty("accessibility")
    private String accessibility;

    @JsonProperty("organisationId")
    private UUID organisationId;

    @JsonProperty("defaultAgencyId")
    private UUID defaultAgencyId;

    @JsonProperty("packagingVente")
    private String packagingVente;

    @JsonProperty("packagingAchat")
    private String packagingAchat;

    @JsonProperty("categorieId")
    private UUID categorieId;

    @JsonProperty("numberUsage")
    private int numberUsage;

    @JsonProperty("transferable")
    private boolean transferable;

    @JsonProperty("state")
    private String state;

    @JsonProperty("maxReservation")
    private int maxReservation;

    @JsonProperty("isTangible")
    private boolean isTangible;

    @JsonProperty("expiresAt")
    private String expiresAt;

    @JsonProperty("productCompositions")
    private List<ProductComposition> productCompositions;
}
