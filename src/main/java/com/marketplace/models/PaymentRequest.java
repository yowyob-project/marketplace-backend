package com.marketplace.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PaymentRequest {

    @JsonProperty("transaction_amount")
    private double transactionAmount;

    @JsonProperty("transaction_currency")
    private String transactionCurrency = "XAF";

    @JsonProperty("transaction_method")
    private String transactionMethod = "MOBILE";

    @JsonProperty("transaction_reference")
    private String transactionReference = UUID.randomUUID().toString();

    @JsonProperty("payer_reference")
    private String payerReference = "415f56db-0343-47aa-a522-9b7b30ee8600";

    @JsonProperty("payer_name")
    private String payerName;

    @JsonProperty("payer_phone_number")
    private String payerPhoneNumber;

    @JsonProperty("payer_lang")
    private String payerLang;

    @JsonProperty("payer_email")
    private String payerEmail;

    @JsonProperty("service_reference")
    private String serviceReference = "415f56db-0343-47aa-a522-9b7b30ee8600";

    @JsonProperty("service_name")
    private String serviceName = "YowYob MarketPlace";

    @JsonProperty("service_description")
    private String serviceDescription = "YowYob MarketPlace Service";

    @JsonProperty("service_quantity")
    private int serviceQuantity = 1;
}
