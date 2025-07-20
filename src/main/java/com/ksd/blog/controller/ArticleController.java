package com.ksd.blog.controller;

import com.ksd.blog.dto.ArticleDTO;
import com.ksd.blog.entity.Article;
import com.ksd.blog.entity.ArticleTag;
import com.ksd.blog.entity.User;
import com.ksd.blog.repository.UserRepository;
import com.ksd.blog.service.IArticleService;
import com.ksd.blog.service.IArticleTagService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/articles")
//@RequestMapping("/api")
@RequiredArgsConstructor
public class ArticleController {

    private final IArticleService articleService;
    private final IArticleTagService tagService;
    @Autowired
    private UserRepository userRepository;
    // 修复构造函数注入
//    public ArticleController(IArticleService articleService, IArticleTagService tagService) {
//        this.articleService = articleService;
//        this.tagService = tagService;
//    }

    // 获取所有文章
    @GetMapping("/all")
    public List<Article> getAll() {
        return articleService.findAll();
    }

    // 根据ID获取文章
    @GetMapping("/{id}")
    public Article getById(@PathVariable String id) {
        return articleService.findById(id).orElse(null);
    }

    // 新增文章
//    @PostMapping("/")
//    public Article create(@RequestBody Article article) {
//        return articleService.saveArticle(article);
//    }
    // 新增文章
//    @PostMapping
//    public ResponseEntity<?> createArticle(@RequestBody ArticleDTO dto) {
//        // 1. 验证 DTO 参数
//        if (dto.getArticleTitle() == null || dto.getArticleTitle().isEmpty()) {
//            return ResponseEntity.badRequest().body("标题不能为空");
//        }
//
//        // 2. DTO 转换为实体类（核心步骤）
//        Article article = new Article();
//        // 复制属性（根据实际字段名对应）
//        article.setArticleTitle(dto.getArticleTitle());       // 标题
//        article.setArticleContent(dto.getArticleContent());   // 内容
//        article.setArticleStatus(dto.getArticleStatus());            // 状态（草稿/发布）
//        article.setCoverImg(dto.getCoverImg());               // 封面图
//        article.setArticleAddTime(LocalDateTime.now());                // 创建时间（后端生成）
//        article.setArticleViewCount(0);                       // 初始阅读量 0
//
//        // 3. 处理标签（如果需要）
//        if (dto.getTags() != null) {
//            article.setTags(String.join(",", dto.getTags()));  // 标签数组转字符串存储
//        }
//
//        // 4. 调用服务层（此时参数类型匹配）
//        Article savedArticle = articleService.saveArticle(article);
//
//        return ResponseEntity.status(201).body(savedArticle);
//    }
    @PostMapping
//    public ResponseEntity<?> createArticle(@RequestBody @Valid ArticleDTO dto,
//                                           Authentication auth) {
//        // 1. 简单校验
//        if (dto.getArticleTitle() == null || dto.getArticleTitle().isEmpty()) {
//            return ResponseEntity.badRequest().body("标题不能为空");
//        }
//
//        // 2. 获取当前登录用户
//        User currentUser = (User) auth.getPrincipal();
//
//        // 3. 保存文章（含作者）
//        Article saved = articleService.createArticle(dto, currentUser);
//
//        // 4. 返回 201 + 带作者的文章
//        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
//    }
//    public ResponseEntity<?> createArticle(@RequestBody ArticleDTO dto,
//                                           HttpSession session) {
//        UserInfo userInfo = (UserInfo) session.getAttribute("loginUser");
//        if (userInfo == null) {
//            return ResponseEntity.status(401).body("未登录");
//        }
//        // 用 ID 再查实体
//        User user = userRepository.findById(userInfo.getUserId())
//                .orElseThrow(() -> new RuntimeException("用户不存在"));
//        Article saved = articleService.createArticle(dto, user);
//        return ResponseEntity.status(201).body(saved);
//    }

    public ResponseEntity<?> createArticle(@RequestBody ArticleDTO dto,
                                           HttpSession session) {
        User user = (User) session.getAttribute("loginUser");   // ← 改这里
        if (user == null) {
            return ResponseEntity.status(401).body("未登录");
        }
        Article saved = articleService.createArticle(dto, user);
        return ResponseEntity.status(201).body(saved);
    }

    // 根据用户ID查询文章
    @GetMapping("/user/{userId}")
    public List<Article> getByUserId(@PathVariable String userId) {
        return articleService.findByUserId(userId);
    }

    // 删除文章
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        articleService.deleteById(id);
    }

//    @PutMapping("/id")
//    public void update()


    // 获取所有标签
    @GetMapping("/tags")
    public List<ArticleTag> getAllTags() {
        return tagService.findAllTags();
    }

    // 按标签ID查询文章
    @GetMapping("/tag/{tagId}")
    public List<Article> getArticlesByTagId(@PathVariable String tagId) {
        return articleService.findByTagId(tagId);
    }

    // 统计标签下的文章数量
    @GetMapping("/tag/{tagId}/count")
    public long getArticleCountByTagId(@PathVariable String tagId) {
        return articleService.countByTagId(tagId);
    }

    // 获取最新文章
    @GetMapping("/latest")
    public List<Article> getLatestArticles(@RequestParam int limit) {
        return articleService.findLatest(limit);
    }

    // 获取最热门文章
    @GetMapping("/hot")
    public List<Article> getHottestArticles(@RequestParam int limit) {
        return articleService.findLatest(limit);
    }



//    // 点赞
//    @PostMapping("/{id}/like")
//    public Result<Long> like(@PathVariable String id,
//                             @RequestParam String userId) {
//        return Result.ok(articleService.likeArticle(id, userId));
//    }
//
//    // 取消点赞
//    @DeleteMapping("/{id}/like")
//    public Result<Long> unlike(@PathVariable String id,
//                               @RequestParam String userId) {
//        return Result.ok(articleService.unlikeArticle(id, userId));
//    }
//
//    // 收藏
//    @PostMapping("/{id}/collect")
//    public Result<Long> collect(@PathVariable String id,
//                                @RequestParam String userId) {
//        return Result.ok(articleService.collectArticle(id, userId));
//    }
//
//    // 取消收藏
//    @DeleteMapping("/{id}/collect")
//    public Result<Long> unCollectArticle(@PathVariable String id,
//                                  @RequestParam String userId) {
//        return Result.ok(articleService.unCollectArticle(id, userId));
//    }
//
//    // 当前文章点赞数
//    @GetMapping("/{id}/like-count")
//    public Result<Long> likeCount(@PathVariable String id) {
//        return Result.ok(articleService.getLikeCount(id));
//    }
//
//    // 当前文章收藏数
//    @GetMapping("/{id}/collect-count")
//    public Result<Long> collectCount(@PathVariable String id) {
//        return Result.ok(articleService.getCollectCount(id));
//    }
//
//    // 用户点赞过的文章
//    @GetMapping("/user/{userId}/liked")
//    public Result<List<Article>> liked(@PathVariable String userId) {
//        return Result.ok(articleService.getUserLikedArticles(userId));
//    }
//
//    // 用户收藏过的文章
//    @GetMapping("/user/{userId}/collected")
//    public Result<List<Article>> collected(@PathVariable String userId) {
//        return Result.ok(articleService.getUserCollectedArticles(userId));
//    }





    private final IArticleService interactService;

    /* ---------- 浏览量 ---------- */
    @PostMapping("/{id}/view")
    public long view(@PathVariable String id,
                     HttpServletRequest request) {
//        String viewer = IpUtil.getIpAddr(request) + "|" + request.getHeader("User-Agent");
        String viewer = Optional.ofNullable(request.getHeader("X-Forwarded-For"))
                .filter(ip -> !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip))
                .orElseGet(() -> request.getRemoteAddr());
        return interactService.viewArticle(id, viewer);
    }

    /* ---------- 点赞 ---------- */
    @PostMapping("/{id}/like")
    public long like(@PathVariable String id,
                     @RequestParam String userId) {
        return interactService.likeArticle(id, userId);
    }

    @DeleteMapping("/{id}/like")
    public long unlike(@PathVariable String id,
                       @RequestParam String userId) {
        return interactService.unlikeArticle(id, userId);
    }

    @GetMapping("/{id}/liked")
    public boolean liked(@PathVariable String id,
                         @RequestParam String userId) {
        return interactService.hasLiked(id, userId);
    }

    @GetMapping("/{id}/like-count")
    public long likeCount(@PathVariable String id) {
        return interactService.getLikeCount(id);
    }

    @GetMapping("/likedBy")
    public List<Article> likedBy(@RequestParam String userId) {
        return interactService.getUserLikedArticles(userId);
    }

    /* ---------- 收藏 ---------- */
    @PostMapping("/{id}/collect")
    public long collect(@PathVariable String id,
                        @RequestParam String userId) {
        return interactService.collectArticle(id, userId);
    }

    @DeleteMapping("/{id}/collect")
    public long unCollect(@PathVariable String id,
                          @RequestParam String userId) {
        return interactService.unCollectArticle(id, userId);
    }

    @GetMapping("/{id}/collected")
    public boolean collected(@PathVariable String id,
                             @RequestParam String userId) {
        return interactService.hasCollected(id, userId);
    }

    @GetMapping("/{id}/collect-count")
    public long collectCount(@PathVariable String id) {
        return interactService.getCollectCount(id);
    }

    @GetMapping("/collectedBy")
    public List<Article> collectedBy(@RequestParam String userId) {
        return interactService.getUserCollectedArticles(userId);
    }
}