package com.ksd.blog.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Entity
@Table(name = "`article_tag`")
public class ArticleTag{
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "article_tag_id", length = 36, nullable = false, updatable = false)
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String articleTagId;

	@Column(name = "article_tag_name")
	private String articleTagName;

	@Column(name = "article_tag_add_time")
	private LocalDateTime articleTagAddTime;
}