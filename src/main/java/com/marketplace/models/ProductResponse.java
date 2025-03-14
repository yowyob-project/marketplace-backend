package com.marketplace.models;

import com.marketplace.models.enumeration.Accessibility;
import com.marketplace.models.enumeration.PackagingType;
import com.marketplace.models.enumeration.ProductState;
import com.marketplace.models.enumeration.ProductType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public interface ProductResponse {
    public UUID getId();
    public ProductBrand getProductBrand();
    public String getImmatriculation();
    public String getSerialNumber();
    public String getSkuCode();
    public String getBarCode();
    public String getQrCode();
    public String getName();
    public String getShortDescription();
    public String getLongDescription();
    public String getStorageCondition();
    public Model getModel();
    public String getIotNumber();
    public Integer getAvailableQuantity();
    public ProductType getProductType();
    public BigDecimal getBasePrice();
    public Accessibility getAccessibility();
    public UUID getOrganisationId();
    public UUID getDefaultAgencyId();
    public PackagingType getPackagingVente();
    public PackagingType getPackagingAchat();
    public Integer getNumberUsage();
    public Boolean getTransferable();
    public ProductState getState();
    public Integer getMaxReservation();
    public Boolean getIsTangible();
    public LocalDateTime getCreatedAt();
    public LocalDateTime getUpdatedAt();
    public LocalDateTime getExpiresAt();

    // Setters
    public void setId(UUID id);
    public void setProductBrand(ProductBrand productBrand);
    public void setImmatriculation(String immatriculation);
    public void setSerialNumber(String serialNumber);
    public void setSkuCode(String skuCode);
    public void setBarCode(String barCode);
    public void setQrCode(String qrCode);
    public void setName(String name);
    public void setShortDescription(String shortDescription);
    public void setLongDescription(String longDescription);
    public void setStorageCondition(String storageCondition);
    public void setModel(Model model);
    public void setIotNumber(String iotNumber);
    public void setAvailableQuantity(Integer availableQuantity);
    public void setProductType(ProductType productType);
    public void setBasePrice(BigDecimal basePrice);
    public void setAccessibility(Accessibility accessibility);
    public void setOrganisationId(UUID organisationId);
    public void setDefaultAgencyId(UUID defaultAgencyId);
    public void setPackagingVente(PackagingType packagingVente);
    public void setPackagingAchat(PackagingType packagingAchat);
    public void setCategorieId(UUID parentId);
    public void setNumberUsage(Integer numberUsage);
    public void setTransferable(Boolean transferable);
    public void setState(ProductState state);
    public void setMaxReservation(Integer maxReservation);
    public void setIsTangible(Boolean isTangible);
    public void setCreatedAt(LocalDateTime createdAt);
    public void setUpdatedAt(LocalDateTime updatedAt);
    public void setExpiresAt(LocalDateTime expiresAt);
}
