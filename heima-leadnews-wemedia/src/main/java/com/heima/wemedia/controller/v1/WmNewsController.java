package com.heima.wemedia.controller.v1;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.WmNewsDto;
import com.heima.model.wemedia.dtos.WmNewsPageReqDto;
import com.heima.wemedia.WmNewsControllerApi;
import com.heima.wemedia.service.WmNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: tang
 * @date: Create in 10:18 2021/9/4
 * @description: 内容管理
 */
@RestController
@RequestMapping("api/v1/news")
public class WmNewsController implements WmNewsControllerApi {

    @Autowired
    private WmNewsService wmNewsService;

    @PostMapping("list")
    @Override
    public ResponseResult findAll(@RequestBody WmNewsPageReqDto dto) {
        return wmNewsService.findAll(dto);
    }

    @PostMapping("submit")
    @Override
    public ResponseResult submitNews(@RequestBody WmNewsDto dto) {
        return wmNewsService.submitNews(dto);
    }

    @GetMapping("one/{id}")
    @Override
    public ResponseResult findOne(@PathVariable("id") Integer newsId) {
        return wmNewsService.findOne(newsId);
    }

    @GetMapping("del_news/{id}")
    @Override
    public ResponseResult deleteOne(@PathVariable("id") Integer newsId) {
        return wmNewsService.deleteOne(newsId);
    }

    @PostMapping("down_or_up")
    @Override
    public ResponseResult downOrUp(@RequestBody WmNewsDto dto) {
        return wmNewsService.downOrUp(dto);
    }
}
