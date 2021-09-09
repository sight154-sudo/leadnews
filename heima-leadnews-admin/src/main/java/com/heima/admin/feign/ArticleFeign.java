package com.heima.admin.feign;

import com.heima.model.article.pojos.ApArticle;
import com.heima.model.article.pojos.ApArticleConfig;
import com.heima.model.article.pojos.ApArticleContent;
import com.heima.model.article.pojos.ApAuthor;
import com.heima.model.common.dtos.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author: tang
 * @date: Create in 21:46 2021/9/6
 * @description:
 */
@FeignClient("leadnews-article")
public interface ArticleFeign {

    /**
     * 保存文章配置信息
     * @param apArticleConfig
     * @return
     */
    @PostMapping("/api/v1/article_config/save")
    ResponseResult saveArticleConfig(ApArticleConfig apArticleConfig);

    /**
     * 查询文章作者信息
     * @param name
     * @return
     */
    @GetMapping("/api/v1/author/findByName/{name}")
    ApAuthor selectAuthorByName(@PathVariable("name") String name);

    /**
     * 保存文章详情信息
     * @param apArticle
     * @return
     */
    @PostMapping("/api/v1/article/save")
    ApArticle saveArticle(ApArticle apArticle);

    /**
     * 保存文章内容
     * @param apArticleContent
     * @return
     */
    @PostMapping("/api/v1/article_content/save")
    ResponseResult saveArticleContent(ApArticleContent apArticleContent);
}
