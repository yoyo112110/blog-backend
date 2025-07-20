package com.ksd.blog.repository;

import com.ksd.blog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, String> {
    // 根据文章ID查询评论，按时间倒序
    List<Comment> findByArticleIdOrderByCommentTimeDesc(String articleId);
    // 根据用户ID查询评论，按时间倒序
    List<Comment> findByUserIdOrderByCommentTimeDesc(String userId);
    // 统计文章的评论数
    Long countByArticleId(String articleId);
    // 批量查询评论（带用户信息）
    List<Comment> findByCommentIdIn(List<String> commentIds);
    // 使用 JOIN FETCH 强制加载 user 关联（解决延迟加载问题）
    @Query("SELECT c FROM Comment c JOIN FETCH c.user WHERE c.articleId = :articleId ORDER BY c.commentTime DESC")
    List<Comment> findByArticleIdWithUser(String articleId);
}
