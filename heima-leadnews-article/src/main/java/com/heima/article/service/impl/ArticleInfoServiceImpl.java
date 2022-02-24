package com.heima.article.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.heima.article.feign.BehaviorFeign;
import com.heima.article.feign.UserFeign;
import com.heima.article.mapper.ApArticleConfigMapper;
import com.heima.article.mapper.ApArticleContentMapper;
import com.heima.article.mapper.ApCollectionMapper;
import com.heima.article.service.ApCollectionService;
import com.heima.article.service.ArticleInfoService;
import com.heima.model.article.dtos.ArticleInfoDto;
import com.heima.model.article.pojos.ApArticleConfig;
import com.heima.model.article.pojos.ApArticleContent;
import com.heima.model.behavior.dtos.ApBehaviorEntryDto;
import com.heima.model.behavior.pojos.ApBehaviorEntry;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.utils.threadlocal.WmThreadLocalUtils;
import com.mysql.fabric.HashShardMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: tang
 * @date: Create in 15:31 2021/9/10
 * @description:
 */
@Service
public class ArticleInfoServiceImpl implements ArticleInfoService {

    @Autowired
    private ApArticleConfigMapper apArticleConfigMapper;
    @Autowired
    private ApArticleContentMapper apArticleContentMapper;

    @Autowired
    private ApCollectionService apCollectionService;

    @Autowired
    private BehaviorFeign behaviorFeign;

    @Autowired
    private UserFeign userFeign;

    @Override
    public ResponseResult findArticleInfo(ArticleInfoDto dto) {
        if (ObjectUtil.isEmpty(dto)) {
            return ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.PARAM_INVALID);
        }
        Map resMap = new HashMap(2);
        ApArticleConfig config = apArticleConfigMapper.findByArticleId(dto.getArticleId());
        if (ObjectUtil.isEmpty(config)) {
            return ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        resMap.put("config", config);
        ApArticleContent articleContent = apArticleContentMapper.findByArticleId(dto.getArticleId());
        if (ObjectUtil.isEmpty(articleContent)) {
            return ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        resMap.put("content", articleContent);
        return ResponseResult.okResult(resMap);
    }

    /**
     * 加载文章详情的行为内容
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult loadArticleBehavior(ArticleInfoDto dto) {
        //参数校验
        if (!ObjectUtil.isAllNotEmpty(dto, dto.getAuthorId(), dto.getArticleId())) {
            return ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.PARAM_INVALID);
        }
        //查询用户实体行为
        Integer userId = WmThreadLocalUtils.get().getId();
        int type = userId == 0 ? 0 : 1;
        ApBehaviorEntryDto apBehaviorEntryDto = new ApBehaviorEntryDto();
        apBehaviorEntryDto.setEntryId(userId == 0 ? dto.getEquipmentId() : userId);
        apBehaviorEntryDto.setType((short) type);
        ApBehaviorEntry behaviorEntry = behaviorFeign.findByTypeAndEntryId(apBehaviorEntryDto);
        if (ObjectUtil.isEmpty(behaviorEntry)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        //{"isfollow":true,"islike":true,"isunlike":false,"iscollection":true}
        Boolean isfollow = false, islike = false, isunlike = false, iscollection = false;
        //查询数据
        isfollow = userFeign.isFollow(userId, dto.getAuthorId());
        islike = behaviorFeign.findLikeByArticleIdAndEntryId(behaviorEntry.getId(),dto.getArticleId(),(short)0);
        isunlike = behaviorFeign.findUnLikeByArticleIdAndEntryId(behaviorEntry.getId(),dto.getArticleId());
        iscollection = apCollectionService.isCollection(behaviorEntry.getId(), dto.getArticleId());
        Map<String,Object> resMap = new HashMap<>();
        resMap.put("isfollow",isfollow);
        resMap.put("islike",islike);
        resMap.put("isunlike",isunlike);
        resMap.put("iscollection",iscollection);
        return ResponseResult.okResult(resMap);
    }
}
