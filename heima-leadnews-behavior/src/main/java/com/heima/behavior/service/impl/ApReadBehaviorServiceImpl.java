package com.heima.behavior.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.heima.behavior.mapper.ApReadBehaviorMapper;
import com.heima.behavior.service.ApBehaviorEntryService;
import com.heima.behavior.service.ApReadBehaviorService;
import com.heima.model.behavior.dtos.ReadBehaviorDto;
import com.heima.model.behavior.pojos.ApBehaviorEntry;
import com.heima.model.behavior.pojos.ApReadBehavior;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.utils.common.SnowflakeIdWorker;
import com.heima.utils.threadlocal.WmThreadLocalUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author: tang
 * @date: Create in 18:25 2021/9/12
 * @description:
 */
@Service
@Slf4j
@Transactional
public class ApReadBehaviorServiceImpl implements ApReadBehaviorService {

    @Autowired
    private ApReadBehaviorMapper apReadBehaviorMapper;

    @Autowired
    private ApBehaviorEntryService apBehaviorEntryService;

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    /**
     * 保存用户的阅读行为
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult readBehavior(ReadBehaviorDto dto) {
        //校验参数
        if (ObjectUtil.isEmpty(dto)) {
            return ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.PARAM_INVALID);
        }
        //获取当前线程的用户
        Integer userId = WmThreadLocalUtils.get().getId();
        int type = userId == 0 ? 0 : 1;
        //查询行为实体
        ApBehaviorEntry entry = apBehaviorEntryService.findByTypeAndEntryId((short) type, userId==0?dto.getEquipmentId():userId);
        if (ObjectUtil.isEmpty(entry)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST, "实体行为数据不存在");
        }
        //查询用户是否存在阅读数据
        ApReadBehavior apReadBehavior = apReadBehaviorMapper.findByEntryIdAndArticleId(entry.getId(), dto.getArticleId());
        //保存或修改用户行为
        if(ObjectUtil.isEmpty(apReadBehavior)){
            apReadBehavior = new ApReadBehavior();
            Long id = snowflakeIdWorker.nextId();
            apReadBehavior.setId(id);
            apReadBehavior.setCount((short)1);
            apReadBehavior.setArticleId(dto.getArticleId());
            apReadBehavior.setReadDuration(dto.getReadDuration());
            apReadBehavior.setEntryId(entry.getId());
            apReadBehavior.setLoadDuration(dto.getLoadDuration());
            apReadBehavior.setPercentage(dto.getPercentage());
            apReadBehavior.setCreatedTime(new Date());
            apReadBehavior.setUpdatedTime(new Date());
            apReadBehaviorMapper.insert(apReadBehavior);
        }else{
            apReadBehavior.setCount(Convert.toShort((apReadBehavior.getCount() + 1)));
            apReadBehavior.setUpdatedTime(new Date());
            apReadBehaviorMapper.update(apReadBehavior);
        }
        return ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.SUCCESS);
    }
}
