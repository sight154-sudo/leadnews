package com.heima.model.comment.vo;

import com.heima.model.comment.pojos.ApComment;
import lombok.Data;

/**
 * @author king
 */
@Data
public class ApCommentVo extends ApComment {

    /**
     * 0：点赞
     * 1：取消点赞
     */
    private Short operation;
}