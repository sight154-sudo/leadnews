package com.heima.article.controller.v1;

import com.heima.article.ApCollectionControllerApi;
import com.heima.article.service.ApCollectionService;
import com.heima.model.behavior.dtos.CollectionBehaviorDto;
import com.heima.model.common.dtos.ResponseResult;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: tang
 * @date: Create in 20:28 2021/9/12
 * @description:
 */
@RestController
@RequestMapping("api/v1")
public class ApCollectionController implements ApCollectionControllerApi {


    @Autowired
    private ApCollectionService apCollectionService;
    /**
     * 用户收藏文章
     * @param dto
     * @return
     */
    @PostMapping("collection_behavior")
    @Override
    public ResponseResult collectionArticle(@RequestBody CollectionBehaviorDto dto) {
        return apCollectionService.collectionArticle(dto);
    }

    @GetMapping("isCollection")
    @Override
    public Boolean isCollection(@Param("entryId") Integer entryId, @Param("articleId") Long articleId) {
        return null;
    }
}

