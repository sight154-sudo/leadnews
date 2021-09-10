package com.heima.admin.controller.v1;

import com.heima.admin.NewsAuthControllerApi;
import com.heima.admin.feign.WmNewsFeign;
import com.heima.model.admin.dtos.NewsAuthDto;
import com.heima.model.common.dtos.PageResponseResult;
import com.heima.model.common.dtos.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: tang
 * @date: Create in 19:58 2021/9/9
 * @description:
 */
@RestController
@RequestMapping("api/v1/news_auth")
public class NewsAuthController implements NewsAuthControllerApi {

    @Autowired
    private WmNewsFeign wmNewsFeign;

    /**
     * 查询媒体审核列表
     * @param dto
     * @return
     */
    @PostMapping("list")
    @Override
    public PageResponseResult findNews(@RequestBody NewsAuthDto dto) {
        return wmNewsFeign.findListAndAuthorName(dto);
    }

    /**
     * 查询单个媒体审核文章
     * @param id
     * @return
     */
    @GetMapping("one/{id}")
    @Override
    public ResponseResult findNewsById(@PathVariable Integer id) {
        return wmNewsFeign.findNewsAndAuthorNameById(id);
    }

    /**
     * 人工审核通过
     * @param dto
     * @return
     */
    @PostMapping("auth_pass")
    @Override
    public ResponseResult authPass(@RequestBody NewsAuthDto dto) {
        return wmNewsFeign.updateNewsStatus(dto);
    }

    /**
     * 人工审核驳回
     * @param dto
     * @return
     */
    @PostMapping("auth_fail")
    @Override
    public ResponseResult authFail(@RequestBody NewsAuthDto dto) {
        return wmNewsFeign.updateNewsStatus(dto);
    }
}
