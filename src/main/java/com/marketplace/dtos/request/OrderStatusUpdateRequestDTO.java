package com.marketplace.dtos.request;

import com.marketplace.enums.OrderStatus;
import lombok.Data;
import jakarta.validation.constraints.NotNull;

@Data
public class OrderStatusUpdateRequestDTO {
    @NotNull(message = "Status is required")
    private OrderStatus status;

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}