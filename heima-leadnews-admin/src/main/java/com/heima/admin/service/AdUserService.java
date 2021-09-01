package com.heima.admin.service;

import com.heima.model.admin.dtos.AdUserDto;
import com.heima.model.common.dtos.ResponseResult;

/**
 * @author: tang
 * @date: Create in 18:29 2021/8/29
 * @description:
 */
public interface AdUserService {

    /**
     * 用户登陆
     * @param dto
     * @return
     */
    ResponseResult login(AdUserDto dto);
}
