package com.ksd.blog.service;

import com.ksd.blog.dto.ArticleDTO;
import com.ksd.blog.entity.Article;
import com.ksd.blog.entity.User;

import java.util.List;
import java.util.Optional;

public interface IArticleService {

    /** 新增 / 保存文章 */
    Article saveArticle(Article article);

    /** 根据主键查询 */
    Optional<Article> findById(String articleId);

    /** 查询全部文章 */
    List<Article> findAll();

    /** 根据用户 ID 查询文章列表 */
    List<Article> findByUserId(String userId);

    /** 删除文章 */
    void deleteById(String articleId);


    List<Article> findByTagId(String tagId);
    long countByTagId(String tagId);
    List<Article> findLatest(int limit);

//    /* ------------ 点赞相关 ------------ */
//    /** 点赞文章，返回最新点赞数 */
//    long likeArticle(String articleId, String userId);
//
//    /** 取消点赞，返回最新点赞数 */
//    long unlikeArticle(String articleId, String userId);
//
//    /** 用户是否已点赞 */
//    boolean hasLiked(String articleId, String userId);
//
//    /** 获取文章最新点赞数 */
//    long getLikeCount(String articleId);
//
//    /** 用户点赞过的文章列表 */
//    List<Article> getUserLikedArticles(String userId);
//
//
//    /* ------------ 收藏相关 ------------ */
//    /** 收藏文章，返回最新收藏数 */
//    long collectArticle(String articleId, String userId);
//
//    /** 取消收藏，返回最新收藏数 */
//    long unCollectArticle(String articleId, String userId);
//
//    /** 用户是否已收藏 */
//    boolean hasCollected(String articleId, String userId);
//
//    /** 获取文章最新收藏数 */
//    long getCollectCount(String articleId);
//
//    /** 用户收藏过的文章列表 */
//    List<Article> getUserCollectedArticles(String userId);
//
//    Article createArticle(ArticleDTO dto, User currentUser);





    /* ---------- 浏览量 ---------- */
    long viewArticle(String articleId, String viewer);   // viewer = IP 或 用户ID
    boolean hasViewed(String articleId, String viewer);
    long getViewCount(String articleId);

    /* ---------- 点赞 ---------- */
    long likeArticle(String articleId, String userId);
    long unlikeArticle(String articleId, String userId);
    boolean hasLiked(String articleId, String userId);
    long getLikeCount(String articleId);
    List<Article> getUserLikedArticles(String userId);

    /* ---------- 收藏 ---------- */
    long collectArticle(String articleId, String userId);
    long unCollectArticle(String articleId, String userId);
    boolean hasCollected(String articleId, String userId);
    long getCollectCount(String articleId);
    List<Article> getUserCollectedArticles(String userId);

    Article createArticle(ArticleDTO dto, User user);
}