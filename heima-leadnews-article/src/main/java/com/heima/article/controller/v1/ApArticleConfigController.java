package com.heima.article.controller.v1;

import com.heima.article.ApArticleConfigControllerApi;
import com.heima.article.service.ApArticleConfigService;
import com.heima.model.article.pojos.ApArticleConfig;
import com.heima.model.common.dtos.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: tang
 * @date: Create in 21:59 2021/9/6
 * @description:
 */
@RestController
@RequestMapping("/api/v1/article_config")
public class ApArticleConfigController implements ApArticleConfigControllerApi {

    @Autowired
    private ApArticleConfigService apArticleConfigService;

    /**
     * 保存文章配置信息
     * @param apArticleConfig
     * @return
     */
    @PostMapping("/save")
    @Override
    public ResponseResult saveArticleConfig(@RequestBody ApArticleConfig apArticleConfig) {
        return  apArticleConfigService.saveArticleConfig(apArticleConfig);
    }
}
