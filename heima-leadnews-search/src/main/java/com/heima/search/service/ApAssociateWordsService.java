package com.heima.search.service;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.search.dtos.UserSearchDto;

/**
 * @author: tang
 * @date: Create in 18:27 2021/9/16
 * @description:
 */
public interface ApAssociateWordsService {
    /**
     联想词
     @param userSearchDto
     @return
     */
    ResponseResult search(UserSearchDto userSearchDto);

    /**
     联想词 V2
     @param userSearchDto
     @return
     */
    ResponseResult searchV2(UserSearchDto userSearchDto);
}
