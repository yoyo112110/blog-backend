//package com.ksd.blog.repository;
//
//import com.ksd.blog.entity.Article;
//import org.apache.ibatis.annotations.Param;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//
//import java.util.List;
//
//public interface ArticleRepository extends JpaRepository<Article, String> {
//    List<Article> findByUserId(String userId);
//    // 根据文章ID列表查询文章
//    List<Article> findByArticleIdIn(List<String> articleIds);
//
//
//
//    // 按发布时间倒序查询最新文章（取前limit篇）
//    @Query("SELECT a FROM Article a ORDER BY a.articleAddTime DESC")
//    List<Article> findLatestArticles(@Param("limit") int limit);
//
//    // 按文章ID列表查询文章（用于通过标签查文章）
//    @Query("SELECT a FROM Article a WHERE a.articleId IN :articleIds")
//    List<Article> findByArticleIds(@Param("articleIds") List<String> articleIds);
//}
// ArticleRepository.java
package com.ksd.blog.repository;

import com.ksd.blog.entity.Article;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, String> {

    List<Article> findByUserId(String userId);

    // 为了支持按ID列表查询
    List<Article> findAllById(Iterable<String> ids);

    void deleteByUserId(String id);


    @Modifying
    @Query("update Article a set a.articleViewCount       = a.articleViewCount       + :delta where a.id = :id")
    void updateViewCount(@Param("id") Long id, @Param("delta") int delta);

    @Modifying
    @Query("update Article a set a.articleLikeCount       = a.articleLikeCount       + :delta where a.id = :id")
    void updateLikeCount(@Param("id") Long id, @Param("delta") int delta);

    @Modifying
    @Query("update Article a set a.articleCollectionCount = a.articleCollectionCount + :delta where a.id = :id")
    void updateCollectCount(@Param("id") Long id, @Param("delta") int delta);
}

