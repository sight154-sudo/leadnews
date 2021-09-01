package com.heima.user;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.user.dtos.AuthDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author: tang
 * @date: Create in 19:21 2021/8/30
 * @description: 用户实名认证接口
 */
@Api(value = "用户实名认证",tags = "RealName")
public interface ApUserRealnameControllerApi {

    /**
     * 根据条件分页查询用户
     * @param dto
     * @return
     */
    @ApiOperation("根据条件分页条件查询用户审核信息")
    ResponseResult loadListByStatus(@RequestBody AuthDto dto);

    /**
     * 用户认证通过
     * @param dto
     * @return
     */
    @ApiOperation("用户认证通过")
    ResponseResult authPass(AuthDto dto);

    /**
     * 用户认证驳回
     * @param dto
     * @return
     */
    @ApiOperation("用户认证驳回")
    ResponseResult authFail(AuthDto dto);
}
