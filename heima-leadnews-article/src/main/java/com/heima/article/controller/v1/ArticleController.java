package com.heima.article.controller.v1;

import com.heima.article.ArticleControllerApi;
import com.heima.article.service.ArticleService;
import com.heima.model.article.pojos.ApAuthor;
import com.heima.model.common.dtos.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: tang
 * @date: Create in 22:15 2021/8/30
 * @description:
 */
@RestController
@RequestMapping("/api/v1/author")
public class ArticleController implements ArticleControllerApi {

   @Autowired
   private ArticleService articleService;

    @GetMapping("/findByUserId/{id}")
    @Override
    public ApAuthor findByUserId(@PathVariable Integer id) {
        return articleService.findByUserId(id);
    }

    @PostMapping("/save")
    @Override
    public ResponseResult save(@RequestBody ApAuthor apAuthor) {
        return articleService.save(apAuthor);
    }
}
