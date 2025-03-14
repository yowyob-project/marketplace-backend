package com.marketplace.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NotificationRequest {
    @JsonProperty("subject")
    public String subject;

    @JsonProperty("type")
    public String type;

    @JsonProperty("message")
    public String message;

    @JsonProperty("email")
    public String email;

    @JsonProperty("priority")
    public int priority = 0;
}
