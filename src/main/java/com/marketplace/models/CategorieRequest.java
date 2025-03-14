package com.marketplace.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.marketplace.models.enumeration.EtatCatalogue;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CategorieRequest {
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("ownerId")
    private UUID ownerId;
    @JsonProperty("imageId")
    private UUID imageIconId;
    @JsonProperty("parentId")
    private UUID parentId;
    @JsonProperty("shortCode")
    private String shortCode;
    @JsonProperty("etat")
    private EtatCatalogue etat;
}
