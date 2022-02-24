package com.heima.user;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.user.dtos.LoginDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author: tang
 * @date: Create in 9:57 2021/9/11
 * @description:
 */
@Api(value = "手机端用户登陆",tags = "apUserLogin")
public interface ApUserLoginControllerApi {

    /**
     * 手机端用户登陆
     * @param dto
     * @return
     */
    @ApiOperation("用户登陆")
    public ResponseResult login(LoginDto dto);
}
