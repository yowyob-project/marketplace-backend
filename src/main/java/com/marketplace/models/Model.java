package com.marketplace.models;

import lombok.*;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table("models")
public class Model {
    @PrimaryKey
    @Column("id")
    private UUID id;
    @Column("created_by_user_id")
    private UUID createdByUserId;
    @Column("name")
    private String name;
    @Column("description")
    private String description;
}
