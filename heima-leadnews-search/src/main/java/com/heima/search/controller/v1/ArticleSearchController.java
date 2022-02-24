package com.heima.search.controller.v1;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.search.dtos.UserSearchDto;
import com.heima.search.ArticleSearchControllerApi;
import com.heima.search.service.ApArticleSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: tang
 * @date: Create in 17:23 2021/9/14
 * @description:
 */
@RestController
@RequestMapping("api/v1/article/search")
public class ArticleSearchController implements ArticleSearchControllerApi {

    @Autowired
    private ApArticleSearchService apArticleSearchService;
    /**
     * ES文章分页搜索
     * @param dto
     * @return
     */
    @PostMapping("search")
    @Override
    public ResponseResult search(@RequestBody UserSearchDto dto) {
        return apArticleSearchService.search(dto);
    }
}
