package com.ksd.blog.controller;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import com.ksd.blog.entity.ArticleTag;
import com.ksd.blog.service.IArticleTagService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/article-tags")
@RequiredArgsConstructor
public class ArticleTagController {

    private final IArticleTagService tagService;

    // 新增分类/标签
    @PostMapping
    public ArticleTag add(@RequestBody ArticleTag tag) {
        return tagService.save(tag);
    }

    // 删除分类/标签
    @DeleteMapping("/{tagId}")
    public void delete(@PathVariable String tagId) {
        tagService.delete(tagId);
    }

    // 根据ID查询
    @GetMapping("/{tagId}")
    public ArticleTag getOne(@PathVariable String tagId) {
        return tagService.getById(tagId);
    }

    // 查询所有
    @GetMapping
    public List<ArticleTag> getAll() {
        return tagService.getAll();
    }

    // 查询所有分类（一位数ID）
    @GetMapping("/types")
    public List<ArticleTag> getTypes() {
        return tagService.getTypes();
    }

    // 查询某个分类下的标签（如分类ID为"1"，则查询10、11、101等标签）
    @GetMapping("/tags/{typeId}")
    public List<ArticleTag> getTagsByType(@PathVariable String typeId) {
        return tagService.getTagsByTypeId(typeId);
    }
}