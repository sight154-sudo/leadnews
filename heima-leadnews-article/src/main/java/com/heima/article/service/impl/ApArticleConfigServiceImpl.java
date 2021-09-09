package com.heima.article.service.impl;

import com.heima.article.mapper.ApArticleConfigMapper;
import com.heima.article.service.ApArticleConfigService;
import com.heima.model.article.pojos.ApArticleConfig;
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
public class ApArticleConfigServiceImpl implements ApArticleConfigService {

    @Autowired
    private ApArticleConfigMapper apArticleConfigMapper;

    @Value("${mybatis.datacenter-id}")
    private Long datacenterId;

    @Value("${mybatis.workerId}")
    private Long workerId;

    @Transactional
    @Override
    public ResponseResult saveArticleConfig(ApArticleConfig apArticleConfig) {
        long id = new SnowflakeIdWorker(workerId, datacenterId).nextId();
        apArticleConfig.setId(id);
        int res = apArticleConfigMapper.saveAparticelConfig(apArticleConfig);
        return res != 0?ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.SUCCESS):ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.FAILED);
    }
}
