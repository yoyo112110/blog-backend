//package com.ksd.blog.service.impl;
//
//import com.ksd.blog.entity.Article;
//import com.ksd.blog.entity.Comment;
//import com.ksd.blog.repository.CommentRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import java.util.List;
//
//public class CommentService {
//    @Autowired
//    private CommentRepository commentRepository;
//    public List<Comment> getComments() {
//        return commentRepository.findAll();
//    }
//    @Override
//    public List<Article> findAll() {
//        return commentRepository.findAll();
//    }
//}
package com.ksd.blog.service.impl;

import com.ksd.blog.entity.Comment;
import com.ksd.blog.repository.CommentRepository;
import com.ksd.blog.service.ICommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService implements ICommentService {

//    private final CommentRepository repo;
//
//    @Override
//    @Transactional
//    public Comment save(Comment c) {
//        c.setCommentTime(LocalDateTime.now());
//        c.setCommentLikeCount(0);
//        return repo.save(c);
//    }
//
//    @Override
//    public List<Comment> listByArticle(String articleId) {
//        return repo.findByArticleIdOrderByCommentTimeDesc(articleId);
//    }
//
//    @Override
//    public List<Comment> listByUser(String userId) {
//        return repo.findByUserIdOrderByCommentTimeDesc(userId);
//    }
//
//    @Override
//    @Transactional
//    public void delete(String id) {
//        repo.deleteById(id);
//    }
//
//    @Override
//    @Transactional
//    public void like(String id) {
//        repo.findById(id).ifPresent(c -> {
//            c.setCommentLikeCount(c.getCommentLikeCount() + 1);
//            repo.save(c);
//        });
//    }
    private final CommentRepository commentRepository;

    @Override
    public Comment save(Comment comment){
        // 新增评论时自动填充时间和默认点赞数
        if(comment.getCommentTime() == null){
            comment.setCommentTime(LocalDateTime.now());// 自动设置当前时间
        }
        if(comment.getCommentLikeCount() == null){
            comment.setCommentLikeCount(0);// 自动设置点赞为0
        }
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> listByArticleId(String articleId){
        // 按时间倒序查询（最新评论在前）
        return commentRepository.findByArticleIdOrderByCommentTimeDesc(articleId);
    }

    @Override
    public List<Comment> listByUserId(String userId){
        return commentRepository.findByUserIdOrderByCommentTimeDesc(userId);
    }

    @Override
    public void deleteById(String commentId){
        commentRepository.deleteById(commentId);
    }

    @Transactional // 保证点赞操作的原子性
    @Override
    public void likeComment(String commentId){
        // 先查询评论，再更新点赞数（+1）
        commentRepository.findById(commentId).ifPresent(comment -> {
            comment.setCommentLikeCount(comment.getCommentLikeCount() + 1);
            commentRepository.save(comment);
        });
    }

    @Override
    public Long countByArticleId(String articleId) {
        return commentRepository.countByArticleId(articleId);
    }

    @Override
    public Map<String, String> getCommentUserNames(List<String> commentIds) {
        // 查询评论列表（带用户信息）
        List<Comment> comments = commentRepository.findByCommentIdIn(commentIds);

        // 提取用户ID和名称的映射
        return comments.stream()
                .filter(comment -> comment.getUser() != null)
                .collect(Collectors.toMap(
                        Comment::getUserId,
                        comment -> comment.getUser().getUserName(),
                        (existing, replacement) -> existing // 处理重复键的策略
                ));
    }

    // 查询文章的评论（包含用户信息）
    public List<Comment> getCommentsByArticleId(String articleId) {
        return commentRepository.findByArticleIdWithUser(articleId);
    }
}