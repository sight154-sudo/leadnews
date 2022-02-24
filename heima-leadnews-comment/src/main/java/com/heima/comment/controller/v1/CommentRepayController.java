package com.heima.comment.controller.v1;

import com.heima.comment.CommentRepayControllerApi;
import com.heima.comment.service.CommentRepayService;
import com.heima.model.comment.dtos.CommentRepayDto;
import com.heima.model.comment.dtos.CommentRepayLikeDto;
import com.heima.model.comment.dtos.CommentRepaySaveDto;
import com.heima.model.common.dtos.ResponseResult;
import org.apache.kafka.streams.state.internals.CompositeReadOnlySessionStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: tang
 * @date: Create in 11:13 2021/9/14
 * @description:
 */
@RestController
@RequestMapping("api/v1/comment_repay")
public class CommentRepayController implements CommentRepayControllerApi {

    @Autowired
    private CommentRepayService commentRepayService;

    /**
     * 加载回复评论
     * @param dto
     * @return
     */
    @PostMapping("load")
    @Override
    public ResponseResult loadCommentRepay(@RequestBody CommentRepayDto dto) {
        return commentRepayService.loadCommentRepay(dto);
    }

    /**
     * 回复评论
     * @param dto
     * @return
     */
    @PostMapping("save")
    @Override
    public ResponseResult saveCommentRepay(@RequestBody CommentRepaySaveDto dto) {
        return commentRepayService.saveCommentRepay(dto);
    }

    /**
     * 点赞回复评论
     * @param dto
     * @return
     */
    @PostMapping("like")
    @Override
    public ResponseResult saveCommentRepayLike(@RequestBody CommentRepayLikeDto dto) {
        return commentRepayService.saveCommentRepayLike(dto);
    }
}
