package com.ksd.blog.dto;

import lombok.Data;

@Data
public class ArticleDTO {
    private String articleTitle;       // 文章标题
    private String articleContent;     // 文章内容（Markdown）
    private String[] tags;             // 标签数组
    private String articleStatus;             // 状态：draft（草稿）/ published（已发布）
    private String coverImg;           // 封面图 URL（可选）
}
