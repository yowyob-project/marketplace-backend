package com.marketplace.entities;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table("Organisation")
public class Organisation {
   @PrimaryKey
    private UUID id;

   @Column("name")
    private String name;

   @Column("description")
    private String description;

   @Column("transferable")
    private boolean transferable;
}