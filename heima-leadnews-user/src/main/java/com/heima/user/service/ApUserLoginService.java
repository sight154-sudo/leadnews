package com.heima.user.service;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.user.dtos.LoginDto;

/**
 * @author: tang
 * @date: Create in 10:04 2021/9/11
 * @description:
 */
public interface ApUserLoginService {

    /**
     * 用户登陆
     * @param dto
     * @return
     */
    public ResponseResult login(LoginDto dto);
}
