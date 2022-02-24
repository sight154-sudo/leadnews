package com.heima.user;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.user.dtos.UserRelationDto;
import io.swagger.annotations.Api;

/**
 * @author: tang
 * @date: Create in 19:22 2021/9/11
 * @description:
 */
@Api(value="用户关注与取消关注",tags = "userRelation")
public interface UserRelationControllerApi {

    /**
     * 关注或取消关注
     * @param dto
     * @return
     */
    ResponseResult follow(UserRelationDto dto);

    /**
     * 是否关注
     * @param userId
     * @param followId
     * @return
     */
    Boolean isFollow(Integer userId,Integer followId);
}
