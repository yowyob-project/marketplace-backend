package com.marketplace.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.marketplace.models.enumeration.PackagingType;
import com.marketplace.models.enumeration.StatusPost;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Builder
public class ProductPostRequest {
    @JsonProperty("marchandId")
    private UUID marchandId;

    @JsonProperty("variationId")
    private UUID variationId;

    @JsonProperty("categorieId")
    private UUID categorieId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("longDescription")
    private String longDescription;

    @JsonProperty("shortDescription")
    private String shortDescription;

    @JsonProperty("lifespan")
    private Integer lifespan;

    @JsonProperty("quantity")
    private Integer quantity;

    @JsonProperty("saleUnit")
    private PackagingType saleUnit;

    @JsonProperty("basePrice")
    private BigDecimal basePrice;

    @JsonProperty("weight")
    private BigDecimal weight;

    @JsonProperty("defaultCurrency")
    private BigDecimal defaultCurrency;

    @JsonProperty("nextAvailableTime")
    private BigDecimal nextAvailableTime;

    @JsonProperty("status")
    private StatusPost status;

    @JsonProperty("expiresAt")
    private LocalDateTime expiresAt;
}
