package com.heima.article.controller.v1;

import com.heima.article.ArticleInfoControllerApi;
import com.heima.article.service.ArticleInfoService;
import com.heima.model.article.dtos.ArticleInfoDto;
import com.heima.model.common.dtos.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: tang
 * @date: Create in 15:22 2021/9/10
 * @description:
 */
@RestController
@RequestMapping("api/v1/article")
public class ArticleInfoController implements ArticleInfoControllerApi {

    @Autowired
    private ArticleInfoService articleInfoService;

    /**
     * 查询文章详情信息
     * @param dto
     * @return
     */
    @PostMapping("load_article_info")
    @Override
    public ResponseResult findArticleInfo(@RequestBody ArticleInfoDto dto) {
        return articleInfoService.findArticleInfo(dto);
    }

    /**
     * 加载文章详情的行为内容
     * @param dto
     * @return
     */
    @PostMapping("/load_article_behavior")
    @Override
    public ResponseResult loadArticleBehavior(@RequestBody ArticleInfoDto dto) {
        return articleInfoService.loadArticleBehavior(dto);
    }
}
