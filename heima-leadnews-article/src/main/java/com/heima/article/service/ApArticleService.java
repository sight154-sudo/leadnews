package com.heima.article.service;

import com.heima.model.article.dtos.ArticleHomeDto;
import com.heima.model.article.pojos.ApArticle;
import com.heima.model.common.dtos.ResponseResult;

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

    /**
     * 根据条件分页展示文章信息
     * @param type
     * @param dto
     * @return
     */
    ResponseResult load(Short type, ArticleHomeDto dto);

    /**
     * 通过id查询文章信息
     * @param id
     * @return
     */
    ApArticle findById(Long id);
}
