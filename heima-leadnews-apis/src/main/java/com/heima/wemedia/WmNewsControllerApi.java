package com.heima.wemedia;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.WmNewsDto;
import com.heima.model.wemedia.dtos.WmNewsPageReqDto;
import com.heima.model.wemedia.pojos.WmNews;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author: tang
 * @date: Create in 10:17 2021/9/4
 * @description: 内容管理接口
 */
@Api(value = "内容管理",tags = "wmNews")
public interface WmNewsControllerApi {
    /**
     * 根据条件查询所有内容
     * @param dto
     * @return
     */
    @ApiOperation("内容列表")
    public ResponseResult findAll(WmNewsPageReqDto dto);

    /**
     * 发布文章
     * @param dto
     * @return
     */
    @ApiOperation("发布文章")
    public ResponseResult submitNews(WmNewsDto dto);

    /**
     * 根据Id查询单个文章
     * @param newsId
     * @return
     */
    @ApiOperation("根据Id查询单个文章")
    public ResponseResult findOne(Integer newsId);


    /**
     * 根据id删除文章
     * @param newsId
     * @return
     */
    @ApiOperation("根据id删除文章")
    public ResponseResult deleteOne(Integer newsId);

    /**
     * 文章的上下架
     * @param dto
     * @return
     */
    @ApiOperation("文章的上下架")
    public ResponseResult downOrUp(WmNewsDto dto);

    /**
     * 远程调用查询文章详情信息
     * @param id
     * @return
     */
    @ApiOperation("远程调用查询文章详情信息")
    public WmNews findById(Integer id);

    /**
     * 远程修改文章状态信息
     * @param wmNews
     * @return
     */
    @ApiOperation("远程修改文章状态信息")
    public ResponseResult updateWmNewsStatus(WmNews wmNews);


}
