package com.marketplace.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.marketplace.enums.OrderStatus;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PaymentResponseCallback {
    @JsonProperty("transaction_code")
    private String transactionCode;

    @JsonProperty("payee_reference")
    private String payeeReference;

    @JsonProperty("payee_name")
    private String payeeName;

    @JsonProperty("transaction_amount")
    private Double transactionAmount;

    @JsonProperty("transaction_fees")
    private Double transactionFees;

    @JsonProperty("transaction_currency")
    private String transactionCurrency;

    @JsonProperty("payer_reference")
    private String payerReference;

    @JsonProperty("payer_name")
    private String payerName;

    @JsonProperty("transaction_type")
    private String transactionType;

    @JsonProperty("transaction_method")
    private String transactionMethod;

    @JsonProperty("transaction_reference")
    private String transactionReference;

    @JsonProperty("operator_reference")
    private String operatorReference;

    @JsonProperty("transaction_status")
    private OrderStatus transactionStatus;
}
