package com.heima.search.service;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.search.dtos.UserSearchDto;

/**
 * @author: tang
 * @date: Create in 20:47 2021/9/14
 * @description:
 */
public interface ApUserSearchService {
    public ResponseResult findUserSearch(UserSearchDto userSearchDto);
    public ResponseResult delUserSearch(UserSearchDto userSearchDto);
}
