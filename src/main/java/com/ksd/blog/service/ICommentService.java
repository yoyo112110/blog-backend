package com.ksd.blog.service;

import com.ksd.blog.entity.Comment;
import java.util.List;
import java.util.Map;

public interface ICommentService {
    // 保存评论（新增、修改）
    Comment save(Comment comment);

    // 根据文章ID查询评论（按时间顺序）
    List<Comment> listByArticleId(String articleId);

    // 根据用户ID查询评论
    List<Comment> listByUserId(String userId);

    // 根据ID删除评论
    void deleteById(String commentId);

    // 点赞评论（+1）
    void likeComment(String commentId);

    // 获取评论数量
    Long countByArticleId(String articleId);

    // 查询评论列表（带用户信息）
    Map<String, String> getCommentUserNames(List<String> commentIds);

    // 查询文章的评论（包含用户信息）
    public List<Comment> getCommentsByArticleId(String articleId);
}