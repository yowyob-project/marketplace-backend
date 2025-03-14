package com.marketplace.models;


import com.fasterxml.jackson.annotation.JsonProperty;

public class PaymentStatusResponse {
    @JsonProperty("status")
    private String status;
    @JsonProperty("message")
    private String message;
    @JsonProperty("data")
    private PaymentData data;
    @JsonProperty("errors")
    private Object errors;
    @JsonProperty("ok")
    private boolean ok;
}
