package com.marketplace.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table("medias")
public class Media implements Serializable{
@Serial
	private static final long serialVersionUID = 1L;
	@PrimaryKey
	@Column("id")
	private UUID id; 
	@Column("target_id")
	private UUID targetId;
	@Column("name")
	private String name;
	@Column("real_name")
	private String realName;
	@Column("size")
	private Long size;
	@Column("file_type")
	private String fileType;
	@Column("is_primary")
	private boolean isPrimary;
}