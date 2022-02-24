package com.heima.article;

import com.heima.model.article.dtos.ArticleHomeDto;
import com.heima.model.common.dtos.ResponseResult;
import io.swagger.annotations.Api;

/**
 * @author: tang
 * @date: Create in 10:08 2021/9/10
 * @description:
 */
@Api(value = "手机端文章展示",tags = "articelHome")
public interface ArticleHomeControllerApi {

    /**
     * 加载首页文章
     * @return
     */
    public ResponseResult load(ArticleHomeDto dto);

    /**
     * 加载更多
     * @return
     */
    public ResponseResult loadMore(ArticleHomeDto dto);

    /**
     * 加载最新
     * @return
     */
    public ResponseResult loadNew(ArticleHomeDto dto);
}
