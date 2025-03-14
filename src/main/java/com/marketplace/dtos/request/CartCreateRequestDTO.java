package com.marketplace.dtos.request;

import lombok.Data;
import java.util.UUID;

@Data
public class CartCreateRequestDTO {
    private UUID userId;
}