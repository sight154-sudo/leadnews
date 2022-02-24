package com.heima.model.comment.dtos;

import com.heima.model.common.annotation.IdEncrypt;
import lombok.Data;

/**
 * 回复评论
 * @author king
 */
@Data
public class CommentSaveDto {

    /**
     * 文章id
     */
    @IdEncrypt
    private Long articleId;

    /**
     * 评论内容
     */
    private String content;
}