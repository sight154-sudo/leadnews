package com.heima.article;

import com.heima.model.article.pojos.ApArticle;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author: tang
 * @date: Create in 19:47 2021/9/8
 * @description:
 */
public interface ApArticleControllerApi{
    /**
     * 保存文章详情信息
     * @param apArticle
     * @return
     */
    @ApiOperation("保存文章详情信息")
    public ApArticle saveArticle(ApArticle apArticle) ;

    /**
     * 通过id查询信息
     * @param id
     * @return
     */
    ApArticle findById(Long id);
}
