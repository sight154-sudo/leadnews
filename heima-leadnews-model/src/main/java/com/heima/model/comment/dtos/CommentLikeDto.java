package com.heima.model.comment.dtos;

import lombok.Data;

/**
 * 点赞评论
 * @author king
 */
@Data
public class CommentLikeDto {

    /**
     * 评论id
     */
    private String commentId;

    /**
     * 0：点赞
     * 1：取消点赞
     */
    private Short operation;
}