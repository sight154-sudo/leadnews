package com.heima.article.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.heima.article.feign.BehaviorFeign;
import com.heima.article.mapper.ApCollectionMapper;
import com.heima.article.service.ApCollectionService;
import com.heima.common.config.IdWorkConfig;
import com.heima.model.behavior.dtos.ApBehaviorEntryDto;
import com.heima.model.behavior.dtos.CollectionBehaviorDto;
import com.heima.model.behavior.pojos.ApBehaviorEntry;
import com.heima.model.behavior.pojos.ApCollection;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.utils.common.SnowflakeIdWorker;
import com.heima.utils.threadlocal.WmThreadLocalUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

/**
 * @author: tang
 * @date: Create in 20:30 2021/9/12
 * @description:
 */
@Service
@Slf4j
@Transactional
public class ApCollectionServiceImpl implements ApCollectionService {

    @Autowired
    private BehaviorFeign behaviorFeign;

    @Autowired
    private ApCollectionMapper apCollectionMapper;

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;
    /**
     * 用户收藏文章
     * @param dto
     * @return
     */
    @Override
    public ResponseResult collectionArticle(CollectionBehaviorDto dto) {
        //校验参数
        if(!ObjectUtil.isAllNotEmpty(dto,dto.getEntryId(),dto.getOperation(),dto.getType())){
            return ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.PARAM_INVALID);
        }
        Integer userId = WmThreadLocalUtils.get().getId();
        int type = userId == 0 ? 0 : 1;
        ApBehaviorEntryDto apBehaviorEntryDto = new ApBehaviorEntryDto();
        apBehaviorEntryDto.setType((short)type);
        apBehaviorEntryDto.setEntryId(userId);
        //查询实体行为
        ApBehaviorEntry apBehaviorEntry = behaviorFeign.findByTypeAndEntryId(apBehaviorEntryDto);
        if(ObjectUtil.isEmpty(apBehaviorEntry)){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST,"实体数据不存在");
        }
        //查询该文章是否已经收藏 若有 则返回 无 则添加
        ApCollection apCollection = apCollectionMapper.findByEntryIdAndArticleId(apBehaviorEntry.getId(), dto.getEntryId());
        if(dto.getOperation().equals((short)0)){
            //收藏
            if(ObjectUtil.isNotEmpty(apCollection)){
                return ResponseResult.errorResult(AppHttpCodeEnum.DATA_EXIST,"已经收藏过了");
            }
            apCollection = new ApCollection();
            apCollection.setArticleId(dto.getEntryId());
            apCollection.setId(snowflakeIdWorker.nextId());
            apCollection.setCollectionTime(new Date());
            apCollection.setPublishedTime(dto.getPublishedTime());
            apCollection.setType(dto.getType());
            apCollection.setEntryId(apBehaviorEntry.getId());
            apCollectionMapper.insert(apCollection);
        }else{
            //取消收藏
            if(ObjectUtil.isEmpty(apCollection)){
                return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST,"未收藏过");
            }
            apCollectionMapper.delete(apCollection);
        }


        return ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public Boolean isCollection(Integer entryId, Long articleId) {
        return apCollectionMapper.findByEntryIdAndArticleId(entryId,articleId)!=null;
    }
}
