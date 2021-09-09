package com.heima.article.controller.v1;

import com.heima.article.ApAuthorControllerApi;
import com.heima.article.service.ApAuthorService;
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
public class ApAuthorController implements ApAuthorControllerApi {



   @Autowired
   private ApAuthorService articleService;

    /**
     * 根据用户id查询作者信息
     * @param id
     * @return
     */
    @GetMapping("/findByUserId/{id}")
    @Override
    public ApAuthor findByUserId(@PathVariable Integer id) {
        return articleService.findByUserId(id);
    }

    /**
     * 保存作者信息
     * @param apAuthor
     * @return
     */
    @PostMapping("/save")
    @Override
    public ResponseResult save(@RequestBody ApAuthor apAuthor) {
        return articleService.save(apAuthor);
    }

    /**
     * 通过姓名查询文章作者信息
     * @param name
     * @return
     */
    @GetMapping("findByName/{name}")
    @Override
    public ApAuthor selectAuthorByName(@PathVariable("name") String name) {
        return articleService.findByName(name);
    }

}
