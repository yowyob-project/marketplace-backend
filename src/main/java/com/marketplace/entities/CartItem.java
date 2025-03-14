//package com.marketplace.entities;
//
//import lombok.Getter;
//import lombok.Setter;
//import org.springframework.data.cassandra.core.mapping.Column;
//import org.springframework.data.cassandra.core.mapping.PrimaryKey;
//import org.springframework.data.cassandra.core.mapping.Table;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.UUID;
//
//@Table("cart_items")
//public class CartItem {
//    @PrimaryKey
//    @Getter
//    @Setter
//    private UUID id;
//
//    @Column("cart_id")
//    @Getter
//    @Setter
//    private UUID cartId;
//
//    @Column("product_id")
//    @Getter
//    @Setter
//    private UUID productId;
//
//    @Getter
//    @Setter
//    private Integer quantity;
//
//    @Column("unit_price")
//    @Getter
//    @Setter
//    private BigDecimal unitPrice;
//
//    @Column("added_at")
//    @Getter
//    @Setter
//    private LocalDateTime addedAt;
//
//    // Getters and Setters
//
//
//    public UUID getId() {
//        return id;
//    }
//
//    public void setId(UUID id) {
//        this.id = id;
//    }
//
//    public UUID getCartId() {
//        return cartId;
//    }
//
//    public void setCartId(UUID cartId) {
//        this.cartId = cartId;
//    }
//
//    public UUID getProductId() {
//        return productId;
//    }
//
//    public void setProductId(UUID productId) {
//        this.productId = productId;
//    }
//
//    public Integer getQuantity() {
//        return quantity;
//    }
//
//    public void setQuantity(Integer quantity) {
//        this.quantity = quantity;
//    }
//
//    public BigDecimal getUnitPrice() {
//        return unitPrice;
//    }
//
//    public void setUnitPrice(BigDecimal unitPrice) {
//        this.unitPrice = unitPrice;
//    }
//
//    public LocalDateTime getAddedAt() {
//        return addedAt;
//    }
//
//    public void setAddedAt(LocalDateTime addedAt) {
//        this.addedAt = addedAt;
//    }
//}



package com.marketplace.entities;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Table("cart_items")
public class CartItem {
    @PrimaryKeyColumn(name = "cart_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private UUID cartId;

    @PrimaryKeyColumn(name = "id", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
    private UUID id;

    @Column("product_id")
    private UUID productId;

    private Integer quantity;

    @Column("unit_price")
    private BigDecimal unitPrice;

    @Column("added_at")
    private LocalDateTime addedAt;


    public UUID getCartId() {
        return cartId;
    }

    public void setCartId(UUID cartId) {
        this.cartId = cartId;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public LocalDateTime getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(LocalDateTime addedAt) {
        this.addedAt = addedAt;
    }
}



