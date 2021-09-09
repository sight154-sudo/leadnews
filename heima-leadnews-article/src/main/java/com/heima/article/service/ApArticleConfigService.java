package com.heima.article.service;

import com.heima.model.article.pojos.ApArticleConfig;
import com.heima.model.common.dtos.ResponseResult;

/**
 * @author: tang
 * @date: Create in 20:22 2021/9/8
 * @description:
 */
public interface ApArticleConfigService {
    /**
     * 保存文章配置信息
     * @param apArticleConfig
     * @return
     */
    public ResponseResult saveArticleConfig(ApArticleConfig apArticleConfig);
}
