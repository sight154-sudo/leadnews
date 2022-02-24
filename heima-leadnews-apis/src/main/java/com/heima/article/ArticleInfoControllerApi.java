package com.heima.article;

import com.heima.model.article.dtos.ArticleInfoDto;
import com.heima.model.common.dtos.ResponseResult;
import io.swagger.annotations.Api;

/**
 * @author: tang
 * @date: Create in 15:16 2021/9/10
 * @description:
 */
@Api(value = "文章详情",tags = "articleInfo")
public interface ArticleInfoControllerApi {

    /**
     * 查询文章详情信息
     * @param dto
     * @return
     */
    public ResponseResult findArticleInfo(ArticleInfoDto dto);

    /**
     * 加载文章详情的行为内容
     * @param dto
     * @return
     */
    ResponseResult loadArticleBehavior( ArticleInfoDto dto);
}
