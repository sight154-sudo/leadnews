package com.heima.wemedia.service;

import com.heima.model.admin.dtos.NewsAuthDto;
import com.heima.model.common.dtos.PageResponseResult;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.WmNewsDto;
import com.heima.model.wemedia.dtos.WmNewsPageReqDto;
import com.heima.model.wemedia.pojos.WmNews;

import java.util.List;

/**
 * @author: tang
 * @date: Create in 10:20 2021/9/4
 * @description:
 */
public interface WmNewsService {
    /**
     * 根据条件查询所有内容列表
     * @param dto
     * @return
     */
    public ResponseResult findAll(WmNewsPageReqDto dto);

    /**
     *发布文章
     * @param dto
     * @return
     */
    public ResponseResult submitNews(WmNewsDto dto);

    /**
     * 根据文章id查询详情信息
     * @param newsId
     * @return
     */
    ResponseResult findOne(Integer newsId);

    /**
     * 根据id删除文章
     * @param newsId
     * @return
     */
    ResponseResult deleteOne(Integer newsId);

    /**
     * 文章的上下架
     * @param dto
     * @return
     */
    ResponseResult downOrUp(WmNewsDto dto);

    /**
     * 修改文章状态
     * @param wmNews
     * @return
     */
    ResponseResult updateWmNews(WmNews wmNews);

    /**
     * 通过id查询自媒体文章信息
     * @param id
     * @return
     */
    WmNews findById(Integer id);

    /**
     * 查询可以发布的文章id
     * @return
     */
    List<Integer> findRelease();

    /**
     * 查询文章信息并关联作者信息
     * @param dto
     * @return
     */
    PageResponseResult findListByDto(NewsAuthDto dto);

    /**
     * 查询单个文章的详情信息包含作者信息
     * @param id
     * @return
     */
    ResponseResult findOneAndAuthorNameById(Integer id);

    /**
     * 修改文章状态信息
     * @param dto
     * @return
     */
    ResponseResult updateWmNewsStatus(NewsAuthDto dto);
}
