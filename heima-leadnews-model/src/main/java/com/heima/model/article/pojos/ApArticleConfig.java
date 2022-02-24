package com.heima.model.article.pojos;

import com.heima.model.common.annotation.IdEncrypt;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: tang
 * @date: Create in 21:08 2021/9/6
 * @description: app已发布文章的配置表
 */
@Data
public class ApArticleConfig implements Serializable {
    /**
     *主键id
     */
    @IdEncrypt
    private Long id;
    /**
     * 文章id
     */
    @IdEncrypt
    private Long articleId;
    /**
     * 是否评论
     */
    private Boolean isComment;
    /**
     * 是否转发
     */
    private Boolean isForward;
    /**
     * 是否下架
     */
    private Boolean isDown;
    /**
     * 是否删除
     */
    private Boolean isDelete;
}
