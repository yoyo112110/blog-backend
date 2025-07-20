package com.ksd.blog.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Entity
@Table(name = "`link`")
public class Link{

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "link_id", length = 36, nullable = false, updatable = false)
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String linkId;
	@Column(name = "link_title")
	private String linkTitle;
	@Column(name = "link_url")
	private String linkUrl;
	@Column(name = "link_logo_url")
	private String linkLogoUrl;
	@Column(name = "link_add_time")
	private LocalDateTime linkAddTime;
}