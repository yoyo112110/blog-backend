package com.ksd.blog.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.ksd.blog.service.IArticleTagService;
import org.springframework.stereotype.Service;
import com.ksd.blog.entity.ArticleTag;
import com.ksd.blog.repository.ArticleTagRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArticleTagService implements IArticleTagService {

    private final ArticleTagRepository tagRepository;

    // 批量查询标签
    @Override
    public List<ArticleTag> getTagsByIds(List<String> tagIds) {
        return tagRepository.findByArticleTagIdIn(tagIds);
    }

    @Override
    public ArticleTag save(ArticleTag tag) {
        // 1. 自动设置添加时间
        if (tag.getArticleTagAddTime() == null) {
            tag.setArticleTagAddTime(LocalDateTime.now());
        }

        // 2. 验证ID规则（一位数=分类，两位数/三位数=标签）
        String tagId = tag.getArticleTagId();
        int idLength = tagId.length();

        if (idLength != 1 && idLength != 2 && idLength != 3) {
            throw new IllegalArgumentException("ID必须是1位（分类）或2-3位（标签）");
        }

        // 3. 标签必须属于某个分类（ID以分类ID开头）
        if (idLength >= 2) {
            String typeId = tagId.substring(0, 1); // 取第一位作为分类ID
            boolean typeExists = tagRepository.existsById(typeId);
            if (!typeExists) {
                throw new IllegalArgumentException("标签ID必须以存在的分类ID（一位数）开头");
            }
        }

        return tagRepository.save(tag);
    }

    @Override
    public void delete(String articleTagId) {
        // 1. 检查是否为分类（一位数）
        if (articleTagId.length() == 1) {
            // 分类删除前，需先删除其下所有标签
            List<ArticleTag> childTags = getTagsByTypeId(articleTagId);
            if (!childTags.isEmpty()) {
                throw new IllegalStateException("无法删除分类，其下存在标签，请先删除标签");
            }
        }

        // 2. 执行删除
        tagRepository.deleteById(articleTagId);
    }

    @Override
    public ArticleTag getById(String articleTagId) {
        return tagRepository.findById(articleTagId).orElse(null);
    }

    @Override
    public List<ArticleTag> getAll() {
        return tagRepository.findAll();
    }

    @Override
    public List<ArticleTag> getTypes() {
        // 筛选出所有一位数ID的分类
        return tagRepository.findAll().stream()
                .filter(tag -> tag.getArticleTagId().length() == 1)
                .collect(Collectors.toList());
    }

    @Override
    public List<ArticleTag> getTagsByTypeId(String typeId) {
        // 筛选出以分类ID开头的两位数/三位数标签
        return tagRepository.findAll().stream()
                .filter(tag -> {
                    String tagId = tag.getArticleTagId();
                    return (tagId.length() == 2 || tagId.length() == 3)
                            && tagId.startsWith(typeId);
                })
                .collect(Collectors.toList());
    }



    @Override
    public List<ArticleTag> findAllTags() {
        return tagRepository.findAll(); // 查询所有标签
    }

    @Override
    public ArticleTag findById(String tagId) {
        return tagRepository.findById(tagId).orElse(null);
    }


}

// ArticleTagServiceImpl.java
//package com.ksd.blog.service.impl;
//
//import com.ksd.blog.entity.ArticleTag;
//import com.ksd.blog.repository.ArticleTagRepository;
//import com.ksd.blog.service.IArticleTagService;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class ArticleTagService implements IArticleTagService {
//
//    private final ArticleTagRepository articleTagRepository;
//
//    public ArticleTagService(ArticleTagRepository articleTagRepository) {
//        this.articleTagRepository = articleTagRepository;
//    }
//
//    @Override
//    public List<ArticleTag> findAllTags() {
//        return articleTagRepository.findAll();
//    }
//
//    @Override
//    public ArticleTag findById(String tagId) {
//        return articleTagRepository.findById(tagId).orElse(null);
//    }
//}