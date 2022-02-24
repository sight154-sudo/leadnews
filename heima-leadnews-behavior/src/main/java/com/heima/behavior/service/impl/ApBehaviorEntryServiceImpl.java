package com.heima.behavior.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.heima.behavior.mapper.ApBehaviorEntryMapper;
import com.heima.behavior.service.ApBehaviorEntryService;
import com.heima.model.behavior.pojos.ApBehaviorEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: tang
 * @date: Create in 9:49 2021/9/12
 * @description:
 */
@Service
public class ApBehaviorEntryServiceImpl implements ApBehaviorEntryService {

    @Autowired
    private ApBehaviorEntryMapper apBehaviorEntryMapper;

    /**
     * 查询用户行为实体
     * @param type
     * @param entryId
     * @return
     */
    @Override
    public ApBehaviorEntry findByTypeAndEntryId(Short type, Integer entryId) {
        return apBehaviorEntryMapper.findByTypeAndEntryId(type,entryId);
    }

    /**
     * 保存用户行为实体
     * @param entry
     */
    @Override
    public void insertApBehaviorEntry(ApBehaviorEntry entry) {
        if(ObjectUtil.isNotEmpty(entry)){
            apBehaviorEntryMapper.insert(entry);
        }
    }
}
