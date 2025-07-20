package com.ksd.blog.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "`article_tag_list`")
public class ArticleTagList{
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "article_tag_list_id", length = 36, nullable = false, updatable = false)
	private String articleTagListId;

	@Column(name = "article_id")
	private String articleId;

	@Column(name = "article_tag_id")
	private String articleTagId;
}