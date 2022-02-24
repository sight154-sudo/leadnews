package com.heima.search;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.search.dtos.UserSearchDto;

/**
 * @author king
 */
public interface ApAssociateWordsControllerApi {

    /**
     联想词
     @param dto
     @return
     */
    ResponseResult search(UserSearchDto dto);
}