package com.heima.article.service.impl;

import com.heima.article.mapper.ApArticleContentMapper;
import com.heima.article.service.ApArticleContentService;
import com.heima.model.article.pojos.ApArticleContent;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.utils.common.SnowflakeIdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: tang
 * @date: Create in 20:23 2021/9/8
 * @description:
 */
@Service
public class ApArticleContentServiceImpl implements ApArticleContentService {

    @Autowired
    private ApArticleContentMapper apArticleContentMapper;

    @Value("${mybatis.datacenter-id}")
    private Long datacenterId;

    @Value("${mybatis.workerId}")
    private Long workerId;

    @Transactional
    @Override
    public ResponseResult saveArticleContent(ApArticleContent apArticleContent) {
        long id = new SnowflakeIdWorker(workerId, datacenterId).nextId();
        apArticleContent.setId(id);
        int res = apArticleContentMapper.saveApArticleContent(apArticleContent);
        return res != 0?ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.SUCCESS):ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.FAILED);
    }
}
