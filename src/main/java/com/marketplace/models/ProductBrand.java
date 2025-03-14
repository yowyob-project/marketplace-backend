package com.marketplace.models;

import lombok.*;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table("product_brand")
public class ProductBrand {
    @PrimaryKey
    @Column("id")
    private UUID id;
    @Column("name")
    private String name;
    @Column("description")
    private String description;
    @Column("brand_code")
    private String brandCode;
    @Column("brand_image_id")
    private UUID brandImageId;
    @Column("created_by_user_id")
    private UUID createdByUserId;
    @Column("notes")
    private String notes;
    @Column("created_at")
    private LocalDateTime createdAt;
    @Column("updated_at")
    private LocalDateTime updatedAt;
}
