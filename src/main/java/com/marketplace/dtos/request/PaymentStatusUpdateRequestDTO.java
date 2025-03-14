package com.marketplace.dtos.request;

import com.marketplace.enums.PaymentStatus;
import lombok.Data;
import jakarta.validation.constraints.NotNull;

@Data
public class PaymentStatusUpdateRequestDTO {
    @NotNull(message = "Payment status is required")
    private PaymentStatus status;

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }
}