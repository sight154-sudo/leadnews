package com.heima.search;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.search.dtos.UserSearchDto;

public interface ApUserSearchControllerApi {


    /**
     * 查询搜索历史
     * @param userSearchDto
     * @return
     */
    public ResponseResult findUserSearch(UserSearchDto userSearchDto) ;

    /**
     * 删除搜索历史
     * @param userSearchDto
     * @return
     */
    public ResponseResult delUserSearch(UserSearchDto userSearchDto) ;

}