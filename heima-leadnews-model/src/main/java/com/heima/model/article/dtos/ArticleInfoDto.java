package com.heima.model.article.dtos;

import com.heima.model.common.annotation.IdEncrypt;
import lombok.Data;

/**
 * @author: tang
 * @date: Create in 15:21 2021/9/10
 * @description:
 */
@Data
public class ArticleInfoDto {

    /**
     * 文章id
     */
    @IdEncrypt
    private Long articleId;
    /**
     * 设备ID
     */
    @IdEncrypt
    private Integer equipmentId;
    /**
     * 作者ID
     */
    @IdEncrypt
    private Integer authorId;
}
