package com.marketplace.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.marketplace.models.enumeration.EtatCatalogue;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CategorieResponse {
    @JsonProperty("id")
    private UUID id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("shortCode")
    private String shortCode;
    @JsonProperty("imageIcon")
    private Media imageIcon;
    @JsonProperty("createdAt")
    private LocalDateTime createdAt;
    @JsonProperty("updatedAt")
    private LocalDateTime updatedAt;
    @JsonProperty("etat")
    private EtatCatalogue etat;
    @JsonProperty("sousCategorie")
    private List<CategorieResponse> sousCategorie;
}
