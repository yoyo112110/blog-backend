//package com.ksd.blog.controller;
//
//import com.ksd.blog.entity.Article;
//import com.ksd.blog.entity.Comment;
//import com.ksd.blog.service.IArticleService;
//import com.ksd.blog.service.ICommentService;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/comments")
//public class CommentController {
//    写
//    private final ICommentService commentService;
//
//    public CommentController(ICommentService commentService) {
//        this.commentService = commentService;
//    }
//
//    // 获取所有评论
//    @GetMapping
//    public List<Comment> getAll() {
//        return commentService.findAll();
//    }
//
//    // 根据ID获取评论
//    @GetMapping("/{id}")
//    public Comment getById(@PathVariable String id) {
//        return commentService.findById(id).orElse(null);
//    }
//
//    // 新增评论
//    @PostMapping
//    public Comment create(@RequestBody Comment comment) {
//        return commentService.saveComment(comment);
//    }
//
//    // 根据用户ID查询评论
//    @GetMapping("/user/{userId}")
//    public List<Article> getByUserId(@PathVariable String userId) {
//        return commentService.findByUserId(userId);
//    }
//
//    // 删除评论
//    @DeleteMapping("/{id}")
//    public void delete(@PathVariable String id) {
//        commentService.deleteById(id);
//    }
//}
package com.ksd.blog.controller;

import com.ksd.blog.entity.Comment;
import com.ksd.blog.service.ICommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final ICommentService commentService;

    // 发表评论
    @PostMapping
    public Comment create(@RequestBody Comment c) {
        return commentService.save(c);
    }

    // 某篇文章的全部评论
//    @GetMapping("/article/{articleId}")
//    public List<Comment> listByArticle(@PathVariable String articleId) {
//        return commentService.listByArticleId(articleId);
//    }
    @GetMapping("/article/{articleId}")
    public ResponseEntity<List<Comment>> getCommentsByArticleId(@PathVariable String articleId) {
        if (articleId == null || articleId.isEmpty()) {
            // 返回 400 错误（参数错误）
            return ResponseEntity.badRequest().body(null);
        }
        List<Comment> comments = commentService.getCommentsByArticleId(articleId);
        // 返回 200 成功 + 数据
        return ResponseEntity.ok(comments);
    }

    // 某用户的全部评论
    @GetMapping("/user/{userId}")
    public List<Comment> listByUser(@PathVariable String userId) {
        return commentService.listByUserId(userId);
    }

    // 删除评论
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        commentService.deleteById(id);
    }

    // 点赞
    @PatchMapping("/{id}/like")
    public void like(@PathVariable String id) {
        commentService.likeComment(id);
    }

    // 获取文章的评论数量
    @GetMapping("/count/article/{articleId}")
    public Long getCommentCountByArticle(@PathVariable String articleId) {
        return commentService.countByArticleId(articleId);
    }

    // 获取多个评论的用户名称映射
    @PostMapping("/user-names")
    public Map<String, String> getCommentUserNames(@RequestBody List<String> commentIds) {
        return commentService.getCommentUserNames(commentIds);
    }
}