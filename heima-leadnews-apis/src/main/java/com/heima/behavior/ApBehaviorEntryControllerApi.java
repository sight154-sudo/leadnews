package com.heima.behavior;

import com.heima.model.behavior.dtos.ApBehaviorEntryDto;
import com.heima.model.behavior.pojos.ApBehaviorEntry;

/**
 * @author: tang
 * @date: Create in 20:36 2021/9/12
 * @description:
 */
public interface ApBehaviorEntryControllerApi {
    /**
     * 查询用户行为实体
     * @param dto
     * @return
     */
    public ApBehaviorEntry findByTypeAndEntryId(ApBehaviorEntryDto dto);
}
