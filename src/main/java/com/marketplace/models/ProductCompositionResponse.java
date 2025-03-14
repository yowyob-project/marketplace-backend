package com.marketplace.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class ProductCompositionResponse {
    @JsonProperty("id")
    private UUID id;
    @JsonProperty("productId")
    private UUID productId;
    @JsonProperty("label")
    private String label;
    @JsonProperty("valueRange")
    private String valueRange;
    @JsonProperty("comment")
    private String comment;
}
