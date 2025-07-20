package com.ksd.blog.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "`ad`")
public class Ad{

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "ad_id", length = 36, nullable = false, updatable = false)
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String adId;

	@Column(name = "ad_type_id")
	private String adTypeId;

	@Column(name = "ad_title")
	private String adTitle;

	@Column(name = "ad_url")
	private String adUrl;

	@Column(name = "ad_sort")
	private Integer adSort;

	@Column(name = "ad_begin_time")
	private LocalDateTime adBeginTime;

	@Column(name = "ad_end_time")
	private LocalDateTime adEndTime;

	@Column(name = "ad_add_time")
	private LocalDateTime adAddTime;

	@Column(name = "status")
	private Integer status;

	// 关联广告类型表（获取广告类型信息）
	@ManyToOne(fetch = FetchType.EAGER) // 立即加载用户信息
	@JoinColumn(name = "ad_type_id", insertable = false, updatable = false)
	private AdType adType;
}
