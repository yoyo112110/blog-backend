//package com.ksd.blog.repository;
//
//import org.apache.ibatis.annotations.Param;
//import org.springframework.data.jpa.repository.JpaRepository;
//import java.util.List;
//import com.ksd.blog.entity.ArticleTagList;
//import org.springframework.data.jpa.repository.Query;
//
//public interface ArticleTagListRepository extends JpaRepository<ArticleTagList, String> {
//    // 根据 articleId 查询所有关联的标签ID
//    List<ArticleTagList> findByArticleId(String articleId);
//    // 查询所有标签ID以 typeId 开头的关联记录（如 typeId=1 → 标签ID=10,11,101等）
//    List<ArticleTagList> findByArticleTagIdStartingWith(String typeId);
//
//
//    // 1. 根据标签ID查询所有关联的中间表记录（用于查该标签下的所有文章ID）
//    @Query("SELECT atl FROM ArticleTagList atl WHERE atl.articleTagId = :tagId")
//    List<ArticleTagList> findByArticleTagId(@Param("tagId") String tagId);
//
//    // 2. 统计某个标签下的文章数量（通过中间表记录数）
//    @Query("SELECT COUNT(atl) FROM ArticleTagList atl WHERE atl.articleTagId = :tagId")
//    long countByArticleTagId(@Param("tagId") String tagId);
//}

// ArticleTagListRepository.java
package com.ksd.blog.repository;

import com.ksd.blog.entity.ArticleTagList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleTagListRepository extends JpaRepository<ArticleTagList, String> {


    List<ArticleTagList> findByArticleTagId(String tagId);

    long countByArticleTagId(String tagId);


    List<ArticleTagList> findByArticleId(String articleId);
}

