package com.heima.wemedia;

import com.heima.model.admin.dtos.AdUserDto;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.WmUserDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author: tang
 * @date: Create in 21:58 2021/9/2
 * @description:
 */
@Api(value = "自媒体平台管理",tags = "login")
public interface LoginControllerApi {
    /**
     * 自媒体人登陆
     * @param dto
     * @return
     */
    @ApiOperation("用户登陆")
    ResponseResult login(@RequestBody WmUserDto dto);
}
