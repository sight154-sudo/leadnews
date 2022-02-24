package com.heima.behavior.service;

import com.heima.model.behavior.pojos.ApBehaviorEntry;

/**
 * @author: tang
 * @date: Create in 9:48 2021/9/12
 * @description:
 */
public interface ApBehaviorEntryService {

    /**
     * 查询用户行为实体
     * @param type
     * @param entryId
     * @return
     */
    public ApBehaviorEntry findByTypeAndEntryId(Short type,Integer entryId);

    /**
     * 保存用户行为实体
     * @param entry
     */
    public void insertApBehaviorEntry(ApBehaviorEntry entry);
}
