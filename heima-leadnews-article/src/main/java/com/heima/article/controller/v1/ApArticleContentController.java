package com.heima.article.controller.v1;

import com.heima.article.ApArticleContentControllerApi;
import com.heima.article.service.ApArticleContentService;
import com.heima.model.article.pojos.ApArticleContent;
import com.heima.model.common.dtos.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: tang
 * @date: Create in 19:45 2021/9/8
 * @description:
 */
@RestController
@RequestMapping("/api/v1/article_content")
public class ApArticleContentController implements ApArticleContentControllerApi {

    @Autowired
    private ApArticleContentService apArticleContentService;

    /**
     * 保存文章内容信息
     * @param apArticleContent
     * @return
     */
    @PostMapping("/save")
    @Override
    public ResponseResult saveArticleContent(@RequestBody ApArticleContent apArticleContent) {
        return apArticleContentService.saveArticleContent(apArticleContent);
    }
}
