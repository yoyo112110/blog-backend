package com.ksd.blog.service.impl;

import com.ksd.blog.common.Role;
import com.ksd.blog.entity.*;
import com.ksd.blog.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
    private final ArticleTagRepository articleTagRepository;
    private final AdRepository adRepository;
    private final LinkRepository linkRepository;

    /* ========== 用户管理 ========== */
    public List<User> listAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("用户不存在"));
    }

    public User addUser(User user) {
        user.setUserRole(Role.USER); // 默认普通用户
        return userRepository.save(user);
    }

    public User updateUser(String id, User updated) {
        User user = getUserById(id);
        user.setUserName(updated.getUserName());
        user.setUserEmail(updated.getUserEmail());
        return userRepository.save(user);
    }

    @Transactional
    public void deleteUser(String id) {
        // 级联删除：先删文章，再删用户
        articleRepository.deleteByUserId(id);
        userRepository.deleteById(id);
    }

    /* ========== 文章管理 ========== */

    public List<Article> listAllArticles() {
        return articleRepository.findAll();
    }

    @Transactional
    public void deleteArticle(String articleId) {
        articleRepository.deleteById(articleId);
    }


    /* ========== 评论管理 ========== */
    public List<Comment> listAllComments() {
        return commentRepository.findAll();
    }
    public void deleteComment(String commentId) {
        commentRepository.deleteById(commentId);
    }

    /* ========== 标签管理 ========== */
    public List<ArticleTag> listAllArticleTags() {
        return articleTagRepository.findAll();
    }
    @Transactional
    public void deleteTag(String tagId) {
        articleTagRepository.deleteById(tagId);
    }

    /* ========== 广告管理 ========== */
    public List<Ad> listAllAds() {
        return adRepository.findAll();
    }
    public Ad addAd(Ad ad) {
        return adRepository.save(ad);
    }

    public void deleteAd(String adId) {
        adRepository.deleteById(adId);
    }

    /* ========== 友链管理 ==========*/
    public List<Link> listAllLinks() {
        return linkRepository.findAll();
    }

    public Link addLink(Link link) {
        return linkRepository.save(link);
    }

    public void deleteLink(String linkId) {
        linkRepository.deleteById(linkId);
    }
}