package com.heima.behavior;

import com.heima.model.behavior.dtos.ReadBehaviorDto;
import com.heima.model.common.dtos.ResponseResult;

/**
 * @author king
 */
public interface ApReadBehaviorControllerApi {

    /**
     * 保存或更新阅读行为
     * @param dto
     * @return
     */
    public ResponseResult readBehavior(ReadBehaviorDto dto);
}