package com.heima.behavior.service;

import com.heima.model.behavior.dtos.UnLikesBehaviorDto;
import com.heima.model.common.dtos.ResponseResult;

/**
 * @author: tang
 * @date: Create in 19:55 2021/9/12
 * @description:
 */
public interface ApUnLikesBehaviorService {
    /**
     * 保存用户不喜欢行为
     * @param dto
     * @return
     */
    ResponseResult unlike(UnLikesBehaviorDto dto);

    /**
     * 查询用户是否不喜欢该文章
     * @param entryId
     * @param articleId
     * @return
     */
    public Boolean findExist(Integer entryId,Long articleId);
}
