package com.marketplace.entities;

import com.marketplace.enums.OrderStatus;
import com.marketplace.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table("orders")
public class Order {
    @PrimaryKey
    private UUID id;  // Changed to simple primary key

    @Column("user_id")
    private UUID userId;

    @Column("order_number")
    private String orderNumber;

    @Column("total_amount")
    private BigDecimal totalAmount;

    @Column("status")
    private OrderStatus status;

    @Column("payment_status")
    private PaymentStatus paymentStatus;

    @Column("payment_attempts")
    private Integer paymentAttempts;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;

    @Column("payment_reference")
    private String payment_reference = "";
//    @PrimaryKeyColumn(name = "user_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
//    private UUID userId;
//
//    @PrimaryKeyColumn(name = "created_at", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
//    private LocalDateTime createdAt;
//
//    @PrimaryKeyColumn(name = "id", ordinal = 2, type = PrimaryKeyType.CLUSTERED)
//    private UUID id;
//
//    @Column("order_number")
//    private String orderNumber;
//
//    @Column("total_amount")
//    private BigDecimal totalAmount;
//
//    private OrderStatus status;
//
//    @Column("payment_status")
//    private PaymentStatus paymentStatus;
//
//    @Column("updated_at")
//    private LocalDateTime updatedAt;


    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Integer getPaymentAttempts() {
        return paymentAttempts;
    }

    public void setPaymentAttempts(Integer paymentAttempts) {
        this.paymentAttempts = paymentAttempts;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getPayment_reference() {
        return payment_reference;
    }

    public void setPayment_reference(String payment_reference) {
        this.payment_reference = payment_reference;
    }

}