package com.heima.user.service;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.user.dtos.AuthDto;

/**
 * @author: tang
 * @date: Create in 19:34 2021/8/30
 * @description:
 */
public interface ApUserRealnameService {
    /**
     * 根据条件分页查询用户审核信息
     * @param dto
     * @return
     */
    ResponseResult loadListByStatus(AuthDto dto);

    /**
     * 修改用户认证状态信息表
     * @param dto
     * @return
     */
    ResponseResult updateStatusById(AuthDto dto,Short status);
}
