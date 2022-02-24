package com.heima.model.comment.dtos;

import com.heima.model.common.annotation.IdEncrypt;
import lombok.Data;

import java.util.Date;

/**
 * 查看评论
 * @author king
 */
@Data
public class CommentDto {

    /**
     * 文章id
     */
    @IdEncrypt
    private Long articleId;

    /**
     * 显示条数
     */
    private Integer size;

    /**
     * 最小点赞数
     */
    private Long minLikes;

}