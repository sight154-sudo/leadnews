package com.heima.comment.service;

import com.heima.model.comment.dtos.CommentDto;
import com.heima.model.comment.dtos.CommentLikeDto;
import com.heima.model.comment.dtos.CommentSaveDto;
import com.heima.model.common.dtos.ResponseResult;

/**
 * @author: tang
 * @date: Create in 19:41 2021/9/13
 * @description:
 */
public interface CommentService {
    /**
     * 保存用户评论
     * @param dto
     * @return
     */
    public ResponseResult saveComment(CommentSaveDto dto) ;

    /**
     * 点赞评论
     * @param dto
     * @return
     */
    ResponseResult like(CommentLikeDto dto);

    /**
     * 查询评论列表
     * @param dto
     * @return
     */
    ResponseResult findByArticleId(CommentDto dto);
}
