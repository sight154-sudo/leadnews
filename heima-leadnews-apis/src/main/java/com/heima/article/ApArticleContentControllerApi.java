package com.heima.article;

import com.heima.model.article.pojos.ApArticleContent;
import com.heima.model.common.dtos.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author: tang
 * @date: Create in 21:52 2021/9/6
 * @description:
 */
@Api(value = "保存文章内容",tags = "apArticleContent")
public interface ApArticleContentControllerApi {
    /**
     * 保存文章内容
     * @param apArticleContent
     * @return
     */
    @ApiOperation("保存文章内容")
    ResponseResult saveArticleContent(ApArticleContent apArticleContent);
}
