package com.heima.comment.feign;

import com.heima.model.article.pojos.ApArticle;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author: tang
 * @date: Create in 20:11 2021/9/13
 * @description:
 */
@FeignClient("leadnews-article")
public interface ArticleFeign {

    /**
     * 通过id查询信息
     * @param id
     * @return
     */
    @GetMapping("api/v1/article/findOne")
    ApArticle findById(@RequestParam("id") Long id);
}
