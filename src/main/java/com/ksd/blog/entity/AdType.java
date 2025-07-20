package com.ksd.blog.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "`ad_type`")
public class AdType{
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "ad_type_id", length = 36, nullable = false, updatable = false)
	private String adTypeId;

	@Column(name = "ad_type_title")
	private String adTypeTitle;

	@Column(name = "ad_type_tag")
	private String adTypeTag;

	@Column(name = "ad_type_sort")
	private Integer adTypeSort;

	@Column(name = "ad_type_add_time")
	private LocalDateTime adTypeAddTime;
}
