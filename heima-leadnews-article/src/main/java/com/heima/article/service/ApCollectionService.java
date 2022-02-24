package com.heima.article.service;

import com.heima.model.behavior.dtos.CollectionBehaviorDto;
import com.heima.model.common.dtos.ResponseResult;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author: tang
 * @date: Create in 20:29 2021/9/12
 * @description:
 */
public interface ApCollectionService {

    /**
     * 用户收藏文章
     * @param dto
     * @return
     */
    public ResponseResult collectionArticle(CollectionBehaviorDto dto);

    /**
     *
     * @param entryId
     * @param articleId
     * @return
     */
    public Boolean isCollection(Integer entryId,Long articleId);
}
