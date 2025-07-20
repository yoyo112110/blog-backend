package com.ksd.blog.service.impl;

import com.ksd.blog.dto.ArticleDTO;
import com.ksd.blog.entity.Article;
import com.ksd.blog.entity.ArticleTagList;
import com.ksd.blog.entity.User;
import com.ksd.blog.repository.ArticleRepository;
import com.ksd.blog.repository.ArticleTagListRepository;
import com.ksd.blog.service.IArticleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleService implements IArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleTagListRepository articleTagListRepository;

//    public ArticleService(ArticleRepository articleRepository,
//                              ArticleTagListRepository articleTagListRepository) {
//        this.articleRepository = articleRepository;
//        this.articleTagListRepository = articleTagListRepository;
//    }

    @Override
    public Article saveArticle(Article article) {
        return articleRepository.save(article);
    }

    @Transactional
    public Article createArticle(ArticleDTO dto, User user) {
        Article article = new Article();
        article.setArticleTitle(dto.getArticleTitle());
        article.setArticleContent(dto.getArticleContent());
        article.setArticleStatus(dto.getArticleStatus());
        article.setCoverImg(dto.getCoverImg());
        article.setArticleAddTime(LocalDateTime.now());
        article.setArticleViewCount(0);
        article.setUser(user);          // 关键：关联作者
        return articleRepository.save(article);
    }

    @Override
    public Optional<Article> findById(String articleId) {
        return articleRepository.findById(articleId);
    }

    @Override
    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    @Override
    public List<Article> findByUserId(String userId) {
        return articleRepository.findByUserId(userId);
    }

    @Override
    public void deleteById(String articleId) {
        articleRepository.deleteById(articleId);
    }

    @Override
    public List<Article> findByTagId(String tagId) {
        // 查询中间表获取所有关联的文章ID
        List<ArticleTagList> tagLists = articleTagListRepository.findByArticleTagId(tagId);
        List<String> articleIds = tagLists.stream()
                .map(ArticleTagList::getArticleId)
                .collect(Collectors.toList());

        // 根据文章ID列表查询文章
        return articleRepository.findAllById(articleIds);
    }

    @Override
    public long countByTagId(String tagId) {
        return articleTagListRepository.countByArticleTagId(tagId);
    }

    @Override
    public List<Article> findLatest(int limit) {
        return articleRepository.findAll(
                PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "articleAddTime"))
        ).getContent();
    }

//    private final ArticleRepository articleRepository;
    private final RedisTemplate<String, String> redisTemplate;

    /* ---------- key 工具 ---------- */
    private String viewKey(String articleId)  { return "article:view:"   + articleId; }
    private String likeKey(String articleId)  { return "article:like:"   + articleId; }
    private String collectKey(String articleId){ return "article:collect:"+ articleId; }

    /* ---------- 浏览量 ---------- */
    @Transactional
    @Override
    public long viewArticle(String articleId, String viewer) {
        Long added = redisTemplate.opsForSet().add(viewKey(articleId), viewer);
        if (added > 0) {
            articleRepository.updateViewCount(Long.valueOf(articleId), 1);
        }
        return getViewCount(articleId);
    }

    @Override
    public boolean hasViewed(String articleId, String viewer) {
        return Boolean.TRUE.equals(
                redisTemplate.opsForSet().isMember(viewKey(articleId), viewer));
    }

    @Override
    public long getViewCount(String articleId) {
        return redisTemplate.opsForSet().size(viewKey(articleId));
    }

    /* ---------- 点赞 ---------- */
    @Transactional
    @Override
    public long likeArticle(String articleId, String userId) {
        Long added = redisTemplate.opsForSet().add(likeKey(articleId), userId);
        if (added > 0) {
            articleRepository.updateLikeCount(Long.valueOf(articleId), 1);
        }
        return getLikeCount(articleId);
    }
    @Transactional
    @Override
    public long unlikeArticle(String articleId, String userId) {
        Long removed = redisTemplate.opsForSet().remove(likeKey(articleId), userId);
        if (removed > 0) {
            articleRepository.updateLikeCount(Long.valueOf(articleId), -1);
        }
        return getLikeCount(articleId);
    }

    @Override
    public boolean hasLiked(String articleId, String userId) {
        return Boolean.TRUE.equals(
                redisTemplate.opsForSet().isMember(likeKey(articleId), userId));
    }

    @Override
    public long getLikeCount(String articleId) {
        return redisTemplate.opsForSet().size(likeKey(articleId));
    }

    @Override
    public List<Article> getUserLikedArticles(String userId) {
        // 通过扫描 Redis 里所有 likeKey 找出包含 userId 的文章
        Set<String> keys = redisTemplate.keys(likeKey("*"));
        if (keys.isEmpty()) return List.of();
        return keys.stream()
                .filter(key -> Boolean.TRUE.equals(
                        redisTemplate.opsForSet().isMember(key, userId)))
                .map(key -> key.substring(likeKey("").length())) // 去掉前缀得到 articleId
                .map(articleRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    /* ---------- 收藏 ---------- */
    @Transactional
    @Override
    public long collectArticle(String articleId, String userId) {
        Long added = redisTemplate.opsForSet().add(collectKey(articleId), userId);
        if (added > 0) {
            articleRepository.updateCollectCount(Long.valueOf(articleId), 1);
        }
        return getCollectCount(articleId);
    }
    @Transactional
    @Override
    public long unCollectArticle(String articleId, String userId) {
        Long removed = redisTemplate.opsForSet().remove(collectKey(articleId), userId);
        if (removed > 0) {
            articleRepository.updateCollectCount(Long.valueOf(articleId), -1);
        }
        return getCollectCount(articleId);
    }

    @Override
    public boolean hasCollected(String articleId, String userId) {
        return Boolean.TRUE.equals(
                redisTemplate.opsForSet().isMember(collectKey(articleId), userId));
    }

    @Override
    public long getCollectCount(String articleId) {
        return redisTemplate.opsForSet().size(collectKey(articleId));

    }

    @Override
    public List<Article> getUserCollectedArticles(String userId) {
        Set<String> keys = redisTemplate.keys(collectKey("*"));
        if (keys.isEmpty()) return List.of();

        return keys.stream()
                .filter(key -> Boolean.TRUE.equals(
                        redisTemplate.opsForSet().isMember(key, userId)))
                .map(key -> key.substring(collectKey("").length()))
                .map(articleRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }
}
