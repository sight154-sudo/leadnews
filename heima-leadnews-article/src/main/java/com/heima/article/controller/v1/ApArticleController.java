package com.heima.article.controller.v1;

import com.heima.article.ApArticleControllerApi;
import com.heima.article.service.ApArticleService;
import com.heima.model.article.pojos.ApArticle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: tang
 * @date: Create in 19:49 2021/9/8
 * @description:
 */
@RestController
@RequestMapping("/api/v1/article")
public class ApArticleController implements ApArticleControllerApi {


    @Autowired
    private ApArticleService apArticleService;

    /**
     * 保存文章详情信息
     * @param apArticle
     * @return
     */
    @PostMapping("/save")
    @Override
    public ApArticle saveArticle(@RequestBody ApArticle apArticle) {

        return apArticleService.saveArticle(apArticle);
    }

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("findOne")
    @Override
    public ApArticle findById(@RequestParam("id") Long id) {
        return apArticleService.findById(id);
    }
}
