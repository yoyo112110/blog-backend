package com.ksd.blog.service;

import java.util.List;

public interface IArticleTagListService {
    // 根据文章ID查询关联的标签ID列表
    List<String> getTagIdsByArticleId(String articleId);
}