package com.ksd.blog.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Entity
@Table(name = "`comment_reply`")
public class CommentReply{

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "comment_reply_id", length = 36, nullable = false, updatable = false)
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String commentReplyId;
	@Column(name = "comment_id")
	private String commentId;
	@Column(name = "reply_user_id")
	private String replyUserId;
	@Column(name = "secondly_id")
	private String secondlyId;
	@Column(name = "comment_reply_time")
	private LocalDateTime commentReplyTime;


}