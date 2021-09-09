package com.heima.wemedia.service;

import com.heima.model.admin.dtos.AdUserDto;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.WmUserDto;
import com.heima.model.wemedia.pojos.WmUser;

/**
 * @author: tang
 * @date: Create in 21:39 2021/8/30
 * @description:
 */
public interface WmUserService {

    /**
     * 保存自媒体用户
     * @param wmUser
     * @return
     */
    ResponseResult save(WmUser wmUser);

    /**
     * 根据自媒体用户名查询
     * @param name
     * @return
     */
    WmUser findByName(String name);

    /**
     * 用户登陆
     * @param dto
     * @return
     */
    ResponseResult login(WmUserDto dto);

    /**
     * 通过id查询自媒体用户信息
     * @param id
     * @return
     */
    WmUser findById(Integer id);
}
