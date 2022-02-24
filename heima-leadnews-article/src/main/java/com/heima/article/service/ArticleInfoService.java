package com.heima.article.service;

import com.heima.model.article.dtos.ArticleInfoDto;
import com.heima.model.common.dtos.ResponseResult;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author: tang
 * @date: Create in 15:30 2021/9/10
 * @description:
 */
public interface ArticleInfoService {

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
    public ResponseResult loadArticleBehavior(ArticleInfoDto dto);
}
