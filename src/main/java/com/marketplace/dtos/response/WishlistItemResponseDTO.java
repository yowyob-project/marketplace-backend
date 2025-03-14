package com.marketplace.dtos.response;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class WishlistItemResponseDTO {
    private UUID id;
    private UUID productId;
    private LocalDateTime addedAt;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public LocalDateTime getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(LocalDateTime addedAt) {
        this.addedAt = addedAt;
    }
}