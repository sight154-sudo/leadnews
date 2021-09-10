package com.heima.admin;

import com.heima.model.admin.dtos.NewsAuthDto;
import com.heima.model.common.dtos.PageResponseResult;
import com.heima.model.common.dtos.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author: tang
 * @date: Create in 19:56 2021/9/9
 * @description: 媒体审核
 */
@Api(value = "媒体审核",tags = "NewsAuth")
public interface NewsAuthControllerApi {

    /**
     * 查询文章列表信息
     * @param dto
     * @return
     */
    @ApiOperation("查询文章列表信息")
    public PageResponseResult findNews(NewsAuthDto dto);

    /**
     * 根据id查询详情信息
     * @param id
     * @return
     */
    @ApiOperation("根据id查询详情信息")
    public ResponseResult findNewsById(Integer id);

    /**
     * 人工审核通过
     * @return
     */
    @ApiOperation("人工审核通过")
    public ResponseResult authPass(NewsAuthDto dto);

    /**
     * 人工审核失败
     * @return
     */
    @ApiOperation("人工审核驳回")
    public ResponseResult authFail(NewsAuthDto dto);
}
