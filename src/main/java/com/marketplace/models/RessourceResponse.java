package com.marketplace.models;

import com.marketplace.models.enumeration.Accessibility;
import com.marketplace.models.enumeration.PackagingType;
import com.marketplace.models.enumeration.ProductState;
import com.marketplace.models.enumeration.ProductType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

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
public class RessourceResponse implements ProductResponse{
    private UUID id;
    private String immatriculation;
    private String serialNumber;
    private String skuCode;
    private String barCode;
    private String qrCode;
    private String name;
    private String shortDescription;
    private String longDescription;
    private String storageCondition;
    private String iotNumber;
    private Integer availableQuantity;
    private ProductType productType;
    private BigDecimal basePrice;
    private Accessibility accessibility;
    private UUID organisationId;
    private UUID defaultAgencyId;
    private PackagingType packagingVente;
    private PackagingType packagingAchat;
    private UUID categorieId;
    private Integer numberUsage;
    private Boolean transferable;
    private ProductState state;
    private Integer maxReservation;
    private Boolean isTangible;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime expiresAt;
    private List<ProductCompositionResponse> productCompositions;
    private Model model;
    private ProductBrand productBrand;
}
