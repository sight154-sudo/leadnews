package com.heima.article.mapper;

import com.heima.model.article.pojos.ApArticle;

/**
 * @author: tang
 * @date: Create in 19:54 2021/9/8
 * @description:
 */
public interface ApArticleMapper {

    /**
     * 保存用户详情信息
     * @param aparticle
     * @return
     */
    int saveArticle(ApArticle aparticle);
}
