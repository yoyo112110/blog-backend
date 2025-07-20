package com.ksd.blog.service.impl;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import com.ksd.blog.entity.ArticleTagList;
import com.ksd.blog.repository.ArticleTagListRepository;
import com.ksd.blog.service.IArticleTagListService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArticleTagListService implements IArticleTagListService {

    private final ArticleTagListRepository tagListRepository;

    @Override
    public List<String> getTagIdsByArticleId(String articleId) {
        // 1. 查询该文章关联的所有 ArticleTagList 记录
        List<ArticleTagList> tagRelations = tagListRepository.findByArticleId(articleId);

        // 2. 提取所有 articleTagId（标签ID）并返回
        return tagRelations.stream()
                .map(ArticleTagList::getArticleTagId)
                .collect(Collectors.toList());
    }
}