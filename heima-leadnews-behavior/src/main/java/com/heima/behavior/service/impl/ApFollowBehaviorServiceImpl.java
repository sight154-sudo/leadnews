package com.heima.behavior.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.ObjectUtil;
import com.heima.behavior.mapper.ApFollowBehaviorMapper;
import com.heima.behavior.service.ApBehaviorEntryService;
import com.heima.behavior.service.ApFollowBehaviorService;
import com.heima.model.behavior.dtos.ApFollowBehaviorDto;
import com.heima.model.behavior.pojos.ApBehaviorEntry;
import com.heima.model.behavior.pojos.ApFollowBehavior;
import com.heima.utils.common.SnowflakeIdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author: tang
 * @date: Create in 8:55 2021/9/12
 * @description:
 */
@Service
public class ApFollowBehaviorServiceImpl implements ApFollowBehaviorService {

    @Autowired
    private ApFollowBehaviorMapper apFollowBehaviorMapper;

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    @Autowired
    private ApBehaviorEntryService apBehaviorEntryService;

    /**
     * 保存用户关注行为
     * @param dto
     */
    @Override
    public void insert(ApFollowBehaviorDto dto) {
        if(ObjectUtil.isEmpty(dto)){
            return;
        }
        //查询行为
        Short type = dto.getEntryId()==0?0: Convert.toShort(dto.getEntryId());
        ApBehaviorEntry entry = apBehaviorEntryService.findByTypeAndEntryId(type, dto.getEntryId());
        if(ObjectUtil.isEmpty(entry)){
            return;
        }
        ApFollowBehavior apFollowBehavior = new ApFollowBehavior();
        BeanUtil.copyProperties(dto,apFollowBehavior);
        apFollowBehavior.setCreatedTime(new Date());
        apFollowBehavior.setEntryId(entry.getId());
        apFollowBehavior.setId(snowflakeIdWorker.nextId());
        apFollowBehaviorMapper.insert(apFollowBehavior);
    }
}
