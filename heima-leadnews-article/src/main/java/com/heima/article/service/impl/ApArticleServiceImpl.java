package com.heima.article.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.heima.article.mapper.ApArticleMapper;
import com.heima.article.service.ApArticleService;
import com.heima.model.article.dtos.ArticleHomeDto;
import com.heima.model.article.pojos.ApArticle;
import com.heima.model.common.dtos.PageResponseResult;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.utils.common.SnowflakeIdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Value("${fdfs.url}")
    private String prefixUrl;

    @Transactional
    @Override
    public ApArticle saveArticle(ApArticle apArticle) {
        long id = new SnowflakeIdWorker(workerId, datacenterId).nextId();
        apArticle.setId(id);
        int res = apArticleMapper.saveArticle(apArticle);
        return res != 0?apArticle:null;
    }

    /**
     * 根据条件分页展示文章信息
     * @param type
     * @param dto
     * @return
     */
    @Override
    public ResponseResult load(Short type, ArticleHomeDto dto) {
        if(!ObjectUtil.isAllNotEmpty(type,dto,dto.getMaxBehotTime(),dto.getMinBehotTime(),dto.getTag())){
            return ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.PARAM_INVALID);
        }
        List<ApArticle> list = apArticleMapper.findList(type, dto);
        ResponseResult result = ResponseResult.okResult(list);
        result.setHost(prefixUrl);
        return result;
    }

    @Override
    public ApArticle findById(Long id) {
        return apArticleMapper.findById(id);
    }
}
