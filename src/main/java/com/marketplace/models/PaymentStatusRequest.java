package com.marketplace.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PaymentStatusRequest {
    @JsonProperty("transaction_code")
    private String transactionCode;
}
