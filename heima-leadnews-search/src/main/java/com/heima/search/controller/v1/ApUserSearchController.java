package com.heima.search.controller.v1;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.search.dtos.UserSearchDto;
import com.heima.search.ApUserSearchControllerApi;
import com.heima.search.service.ApUserSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: tang
 * @date: Create in 20:44 2021/9/14
 * @description: 用户搜索记录
 */
@RestController
@RequestMapping("api/v1/history")
public class ApUserSearchController implements ApUserSearchControllerApi {

    @Autowired
    private ApUserSearchService apUserSearchService;

    @PostMapping("load")
    @Override
    public ResponseResult findUserSearch(@RequestBody UserSearchDto dto) {
        return apUserSearchService.findUserSearch(dto);
    }

    @PostMapping("del")
    @Override
    public ResponseResult delUserSearch(@RequestBody UserSearchDto dto) {
        return apUserSearchService.delUserSearch(dto);
    }
}
