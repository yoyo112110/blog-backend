package com.ksd.blog.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "`comment`")
public class Comment{
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "comment_id", length = 36)
	private String commentId;

	@Column(name = "article_id", nullable = false)
	private String articleId;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "comment_content", columnDefinition = "TEXT", nullable = false)
	private String commentContent;

	@Column(name = "comment_time", nullable = false)
	private LocalDateTime commentTime;

	@Column(name = "comment_like_count")
	private Integer commentLikeCount = 0;

	// 关联用户信息
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	// 移除 @JsonIgnore，添加 @JsonIgnoreProperties 避免循环引用
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", insertable = false, updatable = false)
	private User user;
}