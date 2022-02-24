package com.heima.comment;

import com.heima.model.comment.dtos.CommentDto;
import com.heima.model.comment.dtos.CommentLikeDto;
import com.heima.model.comment.dtos.CommentSaveDto;
import com.heima.model.common.dtos.ResponseResult;

/**
 * @author king
 */
public interface CommentControllerApi {

    /**
     * 保存评论
     * @param dto
     * @return
     */
    public ResponseResult saveComment(CommentSaveDto dto) throws Exception;

    /**
     * 查询评论
     * @param dto
     * @return
     */
    public ResponseResult findByArticleId(CommentDto dto);

    /**
     * 点赞某一条评论
     * @param dto
     * @return
     */
    public ResponseResult like(CommentLikeDto dto);
}