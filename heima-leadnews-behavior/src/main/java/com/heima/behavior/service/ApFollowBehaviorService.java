package com.heima.behavior.service;

import com.heima.model.behavior.dtos.ApFollowBehaviorDto;

/**
 * @author: tang
 * @date: Create in 8:55 2021/9/12
 * @description:
 */
public interface ApFollowBehaviorService {

    /**
     * 保存用户关注行为
     * @param dto
     */
    public void insert(ApFollowBehaviorDto dto);
}
