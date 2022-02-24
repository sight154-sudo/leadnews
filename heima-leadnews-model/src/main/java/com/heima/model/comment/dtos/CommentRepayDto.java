package com.heima.model.comment.dtos;

import lombok.Data;

import java.util.Date;

/**
 * 查询回复评论
 * @author king
 */
@Data
public class CommentRepayDto {

    /**
     * 评论id
     */
    private String commentId;

    /**
     * 数量
     */
    private Integer size;

    /**
     * 最小时间
     */
    private Long minLikes;
}