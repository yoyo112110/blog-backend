//package com.ksd.blog.repository;
//
//import com.ksd.blog.entity.ArticleTag;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//public interface ArticleTagRepository extends JpaRepository<ArticleTag, String>  {
//    // 根据分类编码查询
//    ArticleTag findByArticleTagId(String articleTagId);
//}
//package com.ksd.blog.repository;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import java.util.List;
//import com.ksd.blog.entity.ArticleTag;
//
//public interface ArticleTagRepository extends JpaRepository<ArticleTag, String> {
//    // 无需自定义SQL，通过ID长度和前缀过滤，在服务层处理
//    // 批量查询标签（根据多个 articleTagId）
//    List<ArticleTag> findByArticleTagIdIn(List<String> tagIds);
//}

// ArticleTagRepository.java
package com.ksd.blog.repository;

import com.ksd.blog.entity.ArticleTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleTagRepository extends JpaRepository<ArticleTag, String> {
    // 查询articleTagId在tagIds中的记录
    List<ArticleTag> findByArticleTagIdIn(List<String> tagIds);
    // 根据单个标签ID查询
    ArticleTag findByArticleTagId(String tagId);
}