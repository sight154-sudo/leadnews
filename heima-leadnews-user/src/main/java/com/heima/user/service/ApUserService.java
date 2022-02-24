package com.heima.user.service;

import com.heima.model.user.pojos.ApUser;

/**
 * @author: tang
 * @date: Create in 19:56 2021/9/13
 * @description:
 */
public interface ApUserService {
    /**
     * 通过id查询用户信息
     * @param userId
     * @return
     */
    public ApUser findUserById(Integer userId);
}
