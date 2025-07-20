package com.ksd.blog.controller;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;
import com.ksd.blog.entity.ArticleTag;
import com.ksd.blog.service.IArticleTagListService;
import com.ksd.blog.service.IArticleTagService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ArticleTagListController {

    private final IArticleTagListService tagListService;
    private final IArticleTagService tagService;

    /**
     * 1. 根据文章ID查询关联的标签ID列表
     * 路径：/api/articles/{articleId}/tags
     * 示例：GET /api/articles/1/tags → 返回 ["10", "101"]
     */
    @GetMapping("/articles/{articleId}/tags")
    public List<String> getArticleTagIds(@PathVariable String articleId) {
        return tagListService.getTagIdsByArticleId(articleId);
    }

    /**
     * 2. 批量查询标签名称（根据标签ID列表）
     * 路径：/api/tags/batch
     * 示例：POST /api/tags/batch → 参数 ["10", "101"] → 返回 ["前端", "React"]
     */
//    @PostMapping("/tags/batch")
//    public List<String> getTagNames(@RequestBody List<String> tagIds) {
//        // 查询标签对象列表
//        List<ArticleTag> tags = tagService.getTagsByIds(tagIds);
//        // 提取标签名称并返回
//        return tags.stream()
//                .map(ArticleTag::getArticleTagName)
//                .collect(Collectors.toList());
//    }

    /**
     * 批量查询标签名称?????????????????????????????????????????????????????????
     * 路径：http://localhost:8080/api/tags/batch
     * 方法：POST
     * 请求体：["10", "101"]（标签ID列表）
     * 返回：["前端", "React"]（标签名称列表）
     */
    @PostMapping("/tags/batch") // 完整路径：/api/tags/batch
    public List<String> getTagNames(@RequestBody List<String> tagIds) {
        // 1. 调用Service查询标签对象
        List<ArticleTag> tags = tagService.getTagsByIds(tagIds);

        // 2. 提取标签名称并返回（空值处理：若标签不存在，返回"未知标签"）
        return tags.stream()
                .map(tag -> tag.getArticleTagName() != null ? tag.getArticleTagName() : "未知标签")
                .collect(Collectors.toList());
    }
}