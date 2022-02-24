package com.heima.user.controller.v1;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.user.dtos.UserRelationDto;
import com.heima.user.UserRelationControllerApi;
import com.heima.user.service.UserRelationService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: tang
 * @date: Create in 19:25 2021/9/11
 * @description:
 */
@RestController
@RequestMapping("api/v1/user")
public class UserRelationController implements UserRelationControllerApi {

    @Autowired
    private UserRelationService userRelationService;

    /**
     * 用户关注用户或取消关注
     * @param dto
     * @return
     */
    @PostMapping("user_follow")
    @Override
    public ResponseResult follow(@RequestBody UserRelationDto dto) {
        return userRelationService.follow(dto);
    }


    @GetMapping("isFollow")
    @Override
    public Boolean isFollow(@Param("userId") Integer userId,@Param("followId") Integer followId) {
        return userRelationService.isFollow(userId,followId);
    }
}
