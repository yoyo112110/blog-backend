package com.ksd.blog.repository;

import com.ksd.blog.entity.CommentReply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentReplyRepository extends JpaRepository<CommentReply, String> {
}
