package com.heima.model.article.pojos;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: tang
 * @date: Create in 21:08 2021/9/6
 * @description: 文章内容表
 */
@Data
public class ApArticleContent implements Serializable {
    /**
     * 主键id
     */
    private Long id;
    /**
     * 文章id
     */
    private Long articleId;
    /**
     * 文章内容
     */
    private String content;
}
