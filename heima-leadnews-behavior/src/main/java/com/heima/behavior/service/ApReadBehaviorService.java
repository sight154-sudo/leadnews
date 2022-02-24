package com.heima.behavior.service;

import com.heima.model.behavior.dtos.ReadBehaviorDto;
import com.heima.model.common.dtos.ResponseResult;

/**
 * @author: tang
 * @date: Create in 18:24 2021/9/12
 * @description:
 */
public interface ApReadBehaviorService {
    /**
     * 保存用户阅读行为
     * @param dto
     * @return
     */
    public ResponseResult readBehavior(ReadBehaviorDto dto);
}
