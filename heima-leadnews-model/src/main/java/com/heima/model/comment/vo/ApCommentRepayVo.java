package com.heima.model.comment.vo;

import com.heima.model.comment.pojos.ApCommentRepay;
import lombok.Data;

/**
 * @author king
 */
@Data
public class ApCommentRepayVo extends ApCommentRepay {

    /**
     * 0：点赞
     * 1：取消点赞
     */
    private Short operation;
}