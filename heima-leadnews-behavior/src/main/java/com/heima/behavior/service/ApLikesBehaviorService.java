package com.heima.behavior.service;

import com.heima.model.behavior.dtos.LikesBehaviorDto;
import com.heima.model.common.dtos.ResponseResult;

/**
 * @author: tang
 * @date: Create in 11:09 2021/9/12
 * @description:
 */
public interface ApLikesBehaviorService {
    /**
     * 用户点赞行为
     * @param dto
     * @return
     */
    ResponseResult like(LikesBehaviorDto dto);

    /**
     *
     * @param entryId
     * @param articleId
     * @param type
     * @return
     */
    Boolean findExists(Integer entryId, Long articleId, short type);
}
