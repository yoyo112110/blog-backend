package com.ksd.blog.controller;

import com.ksd.blog.entity.*;
import com.ksd.blog.service.impl.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    /* ========== 用户管理 ========== */

    @GetMapping("/users")
    public List<User> listAllUsers() {
        return adminService.listAllUsers();
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable String id) {
        return adminService.getUserById(id);
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public User addUser(@RequestBody User user) {
        return adminService.addUser(user);
    }

    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable String id, @RequestBody User user) {
        return adminService.updateUser(id, user);
    }

    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable String id) {
        adminService.deleteUser(id);
    }

    /* ========== 文章管理 ========== */
    @GetMapping("/articles")
    public List<Article> listAllArticles(){
        return adminService.listAllArticles();
    }
    @DeleteMapping("/articles/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteArticle(@PathVariable String id) {
        adminService.deleteArticle(id);
    }

    /* ========== 评论管理 ========== */
    @GetMapping("/comments")
    public List<Comment> listAllComments(){
        return adminService.listAllComments();
    }

    @DeleteMapping("/comments/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable String id) {
        adminService.deleteComment(id);
    }

    /* ========== 标签管理 ========== */
    @GetMapping("/tags")
    public List<ArticleTag> listAllTags(){
        return adminService.listAllArticleTags();
    }

    @DeleteMapping("/tags/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTag(@PathVariable String id) {
        adminService.deleteTag(id);
    }

    /* ========== 广告管理 ========== */
    @GetMapping("/ads")
    public List<Ad> listAllAds(){
        return adminService.listAllAds();
    }

    @PostMapping("/ads")
    @ResponseStatus(HttpStatus.CREATED)
    public Ad addAd(@RequestBody Ad ad) {
        return adminService.addAd(ad);
    }

    @DeleteMapping("/ads/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAd(@PathVariable String id) {
        adminService.deleteAd(id);
    }

    /* ========== 友链管理 ========== */
    @GetMapping("/links")
    public List<Link> listAllLinks(){
        return adminService.listAllLinks();
    }

    @PostMapping("/links")
    @ResponseStatus(HttpStatus.CREATED)
    public Link addLink(@RequestBody Link link) {
        return adminService.addLink(link);
    }

    @DeleteMapping("/links/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLink(@PathVariable String id) {
        adminService.deleteLink(id);
    }
}