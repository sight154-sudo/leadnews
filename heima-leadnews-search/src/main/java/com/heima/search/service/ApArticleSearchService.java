package com.heima.search.service;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.search.dtos.UserSearchDto;

/**
 * @author king
 */
public interface ApArticleSearchService {

    /**
     * ES文章分页搜索
     * @param userSearchDto
     * @return
     */
    ResponseResult search(UserSearchDto userSearchDto);
}