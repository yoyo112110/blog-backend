package com.ksd.blog.service;

import java.util.List;
import com.ksd.blog.entity.ArticleTag;

public interface IArticleTagService {
    // 新增/修改分类或标签
    ArticleTag save(ArticleTag tag);

    // 删除分类或标签
    void delete(String articleTagId);

    // 根据ID查询
    ArticleTag getById(String articleTagId);

    // 查询所有分类和标签
    List<ArticleTag> getAll();

    // 查询所有分类（一位数ID）
    List<ArticleTag> getTypes();

    // 查询某个分类下的标签（两位数/三位数ID，且以分类ID开头）
    List<ArticleTag> getTagsByTypeId(String typeId);

    // 批量查询标签
    List<ArticleTag> getTagsByIds(List<String> tagIds);




    // 获取所有标签（分类）
    List<ArticleTag> findAllTags();

    // 按标签ID查询标签详情
    ArticleTag findById(String tagId);
}