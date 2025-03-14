package com.marketplace.entities;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Table("wishlist_items")
public class WishlistItem {
    @PrimaryKeyColumn(name = "wishlist_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private UUID wishlistId;

    @PrimaryKeyColumn(name = "id", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
    private UUID id;

    @Column("product_id")
    private UUID productId;

    @Column("added_at")
    private LocalDateTime addedAt;


    public UUID getWishlistId() {
        return wishlistId;
    }

    public void setWishlistId(UUID wishlistId) {
        this.wishlistId = wishlistId;
    }

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