package com.heima.admin;

import com.heima.model.admin.dtos.AdUserDto;
import com.heima.model.common.dtos.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author: tang
 * @date: Create in 18:18 2021/8/29
 * @description:
 */
@Api(value = "管理员操作",tags = "user")
public interface LoginControllerApi {
    /**
     * 管理员登陆
     * @param dto
     * @return
     */
    @ApiOperation("用户登陆")
    ResponseResult login(@RequestBody AdUserDto dto);
}
