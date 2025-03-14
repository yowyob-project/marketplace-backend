package com.marketplace.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PaymentData {
    @JsonProperty("message")
    private String message;

    @JsonProperty("status_code")
    private int statusCode;

    @JsonProperty("transaction_code")
    private String transactionCode;

    @JsonProperty("transaction_status")
    private String transactionStatus;
}
