package com.heima.model.comment.dtos;

import lombok.Data;

/**
 * 接收对评论进行评论
 * @author king
 */
@Data
public class CommentRepaySaveDto {

    /**
     * 评论id
     */
    private String commentId;

    /**
     * 回复内容
     */
    private String content;
}