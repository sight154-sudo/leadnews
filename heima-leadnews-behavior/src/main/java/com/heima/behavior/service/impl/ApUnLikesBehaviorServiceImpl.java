package com.heima.behavior.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.heima.behavior.mapper.ApUnLikesBehaviorMapper;
import com.heima.behavior.service.ApBehaviorEntryService;
import com.heima.behavior.service.ApUnLikesBehaviorService;
import com.heima.common.config.IdWorkConfig;
import com.heima.model.behavior.dtos.UnLikesBehaviorDto;
import com.heima.model.behavior.pojos.ApBehaviorEntry;
import com.heima.model.behavior.pojos.ApLikesBehavior;
import com.heima.model.behavior.pojos.ApUnlikesBehavior;
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
 * @date: Create in 19:56 2021/9/12
 * @description:
 */
@Service
@Slf4j
@Transactional
public class ApUnLikesBehaviorServiceImpl implements ApUnLikesBehaviorService {

    @Autowired
    private ApBehaviorEntryService apBehaviorEntryService;
    @Autowired
    private ApUnLikesBehaviorMapper apUnLikesBehaviorMapper;
    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    @Override
    public ResponseResult unlike(UnLikesBehaviorDto dto) {
        //参数校验
        if(!ObjectUtil.isAllNotEmpty(dto,dto.getArticleId())){
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
        ApUnlikesBehavior apUnlikesBehavior = apUnLikesBehaviorMapper.findByEntryIdAndArticleId(entry.getId(),dto.getArticleId());
        if(ObjectUtil.isEmpty(apUnlikesBehavior)){
            //新增或修改数据
            apUnlikesBehavior = new ApUnlikesBehavior();
            apUnlikesBehavior.setId(snowflakeIdWorker.nextId());
            apUnlikesBehavior.setCreatedTime(new Date());
            apUnlikesBehavior.setArticleId(dto.getArticleId());
            apUnlikesBehavior.setEntryId(entry.getId());
            apUnlikesBehavior.setType(Integer.valueOf(dto.getType()));
            apUnLikesBehaviorMapper.insert(apUnlikesBehavior);
        }else{
            //存在 则修改
            apUnlikesBehavior.setType(Integer.valueOf(dto.getType()));
            apUnLikesBehaviorMapper.update(apUnlikesBehavior);
        }
        return ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 查询用户是否不喜欢该文章
     * @param entryId
     * @param articleId
     * @return
     */
    @Override
    public Boolean findExist(Integer entryId, Long articleId) {
        if(!ObjectUtil.isAllNotEmpty(entryId,articleId)){
            throw new RuntimeException("参数不合法");
        }
        return apUnLikesBehaviorMapper.findByEntryIdAndArticleId(entryId,articleId)!=null;
    }
}
