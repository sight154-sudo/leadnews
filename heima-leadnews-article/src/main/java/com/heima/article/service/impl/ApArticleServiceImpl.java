package com.heima.article.service.impl;

import com.heima.article.mapper.ApArticleMapper;
import com.heima.article.service.ApArticleService;
import com.heima.model.article.pojos.ApArticle;
import com.heima.utils.common.SnowflakeIdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: tang
 * @date: Create in 19:54 2021/9/8
 * @description:
 */
@Service
public class ApArticleServiceImpl implements ApArticleService {

    @Autowired
    private ApArticleMapper apArticleMapper;

    @Value("${mybatis.datacenter-id}")
    private Long datacenterId;

    @Value("${mybatis.workerId}")
    private Long workerId;

    @Transactional
    @Override
    public ApArticle saveArticle(ApArticle apArticle) {
        long id = new SnowflakeIdWorker(workerId, datacenterId).nextId();
        apArticle.setId(id);
        int res = apArticleMapper.saveArticle(apArticle);
        return res != 0?apArticle:null;
    }
}
