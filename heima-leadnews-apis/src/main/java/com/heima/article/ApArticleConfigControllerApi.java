package com.heima.article;

import com.heima.model.article.pojos.ApArticleConfig;
import com.heima.model.common.dtos.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author: tang
 * @date: Create in 21:51 2021/9/6
 * @description:
 */
@Api(value = "文章配置",tags = "apArticleConfig")
public interface ApArticleConfigControllerApi {

    /**
     * 保存文章配置信息
     * @param apArticleConfig
     * @return
     */
    @ApiOperation("保存文章配置信息")
    ResponseResult saveArticleConfig(ApArticleConfig apArticleConfig);
}
