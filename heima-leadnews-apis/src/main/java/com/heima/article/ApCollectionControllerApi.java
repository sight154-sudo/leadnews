package com.heima.article;

import com.heima.model.behavior.dtos.CollectionBehaviorDto;
import com.heima.model.common.dtos.ResponseResult;

/**
 * @author: tang
 * @date: Create in 20:27 2021/9/12
 * @description:
 */
public interface ApCollectionControllerApi {

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
    Boolean isCollection(Integer entryId,Long articleId);
}
