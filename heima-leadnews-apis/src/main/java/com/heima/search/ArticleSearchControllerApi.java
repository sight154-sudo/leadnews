package com.heima.search;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.search.dtos.UserSearchDto;

/**
 * @author king
 */
public interface ArticleSearchControllerApi {

    /**
     *  搜索文章
     * @param userSearchDto
     * @return
     */
    ResponseResult search(UserSearchDto userSearchDto);
}