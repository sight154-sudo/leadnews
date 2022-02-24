package com.heima.model.comment.dtos;

import lombok.Data;

/**
 * 点赞回复评论
 * @author king
 */
@Data
public class CommentRepayLikeDto {

    /**
     * 回复id
     */
    private String commentRepayId;

    /**
     * 0：点赞
     * 1：取消点赞
     */
    private Short operation;
}