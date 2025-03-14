package com.marketplace.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductComposition {
    @JsonProperty("label")
    private String label;

    @JsonProperty("valueRange")
    private String valueRange;

    @JsonProperty("comment")
    private String comment;
}
