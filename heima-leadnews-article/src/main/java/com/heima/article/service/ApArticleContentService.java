package com.heima.article.service;

import com.heima.model.article.pojos.ApArticleContent;
import com.heima.model.common.dtos.ResponseResult;

/**
 * @author: tang
 * @date: Create in 20:23 2021/9/8
 * @description:
 */
public interface ApArticleContentService {
    /**
     * 保存文章内容
     * @param apArticleContent
     * @return
     */
    ResponseResult saveArticleContent(ApArticleContent apArticleContent) ;
}
