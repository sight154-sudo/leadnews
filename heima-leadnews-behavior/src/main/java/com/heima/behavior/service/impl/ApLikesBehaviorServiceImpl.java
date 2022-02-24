package com.heima.behavior.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.heima.behavior.mapper.ApLikesBehaviorMapper;
import com.heima.behavior.service.ApBehaviorEntryService;
import com.heima.behavior.service.ApLikesBehaviorService;
import com.heima.common.config.IdWorkConfig;
import com.heima.model.behavior.dtos.LikesBehaviorDto;
import com.heima.model.behavior.pojos.ApBehaviorEntry;
import com.heima.model.behavior.pojos.ApLikesBehavior;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.utils.common.SnowflakeIdWorker;
import com.heima.utils.threadlocal.WmThreadLocalUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author: tang
 * @date: Create in 11:10 2021/9/12
 * @description:
 */
@Service
@Slf4j
@Transactional
public class ApLikesBehaviorServiceImpl implements ApLikesBehaviorService {


    @Autowired
    private ApBehaviorEntryService apBehaviorEntryService;

    @Autowired
    private ApLikesBehaviorMapper apLikesBehaviorMapper;

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    /**
     * 用户点赞行为
     * @param dto
     * @return
     */
    @Override
    public ResponseResult like(LikesBehaviorDto dto) {
        //参数校验
        if(!ObjectUtil.isAllNotEmpty(dto,dto.getArticleId(),dto.getOperation(),dto.getType())){
            return ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.PARAM_INVALID);
        }
        Integer userId = WmThreadLocalUtils.get().getId();
        Short type = 0;
        if(0 != userId){
            type = 1;
        }
        ApBehaviorEntry entry = apBehaviorEntryService.findByTypeAndEntryId(type, userId==0?dto.getEquipmentId():userId);
        if(ObjectUtil.isEmpty(entry)){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST,"实体行为数据不存在");
        }
        //是否有数据
        ApLikesBehavior apLikesBehavior = apLikesBehaviorMapper.findByLikeBehavior(entry.getId(),dto.getArticleId(),dto.getType());
        if(ObjectUtil.isEmpty(apLikesBehavior)){
            //新增或修改数据
            apLikesBehavior = new ApLikesBehavior();
            apLikesBehavior.setId(snowflakeIdWorker.nextId());
            apLikesBehavior.setCreatedTime(new Date());
            apLikesBehavior.setOperation(dto.getOperation());
            apLikesBehavior.setArticleId(dto.getArticleId());
            apLikesBehavior.setEntryId(entry.getId());
            apLikesBehavior.setType(dto.getType());
            apLikesBehaviorMapper.insert(apLikesBehavior);
        }else{
            //存在 则修改
            apLikesBehavior.setOperation(dto.getOperation());
            apLikesBehaviorMapper.update(apLikesBehavior);
        }
        return ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public Boolean findExists(Integer entryId, Long articleId, short type) {
        return apLikesBehaviorMapper.findByLikeBehavior(entryId,articleId,type)!=null;
    }
}
