package com.ksd.blog.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "`article`")
public class Article{
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "article_id", length = 36, nullable = false, updatable = false)
	private String articleId;

	@Column(name = "user_id")
	@JsonIgnore
	private String userId;

	@Column(name = "article_title")
	private String articleTitle;

	@Column(name = "article_add_time")
	private LocalDateTime articleAddTime;

	@Column(name = "article_content", columnDefinition = "TEXT")
	private String articleContent;

	@Column(name = "article_like_count")
	private Integer articleLikeCount;

	@Column(name = "article_view_count")
	private Integer articleViewCount;

	@Column(name = "article_collection_count")
	private Integer articleCollectionCount;

	@Column(name = "cover_img")
	private String coverImg;

	@Column(name = "article_status")
	private String articleStatus;

	// 关联用户表（获取作者信息）
	@ManyToOne(fetch = FetchType.EAGER) // 立即加载用户信息
	@JoinColumn(name = "user_id", insertable = false, updatable = false)
	// insertable=false, updatable=false：避免通过Article修改userId
	private User user;
}