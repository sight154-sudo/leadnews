package com.heima.user.service;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.user.dtos.UserRelationDto;
import org.apache.ibatis.annotations.Param;

/**
 * @author: tang
 * @date: Create in 19:26 2021/9/11
 * @description:
 */
public interface UserRelationService {
    /**
     * 用户关注或取消关注
     * @param dto
     * @return
     */
    public ResponseResult follow(UserRelationDto dto);

    /**
     * 是否关注
     * @param userId
     * @param followId
     * @return
     */
    public Boolean isFollow(Integer userId, Integer followId);
}
