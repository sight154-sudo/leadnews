package com.heima.article.service;

import com.heima.model.article.pojos.ApArticle;

/**
 * @author: tang
 * @date: Create in 19:53 2021/9/8
 * @description:
 */
public interface ApArticleService {

    /**
     * 保存文章详情信息
     * @param apArticle
     * @return
     */
    public ApArticle saveArticle(ApArticle apArticle);
}
