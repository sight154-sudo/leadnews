package com.heima.wemedia.service;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.WmNewsDto;
import com.heima.model.wemedia.dtos.WmNewsPageReqDto;
import com.heima.model.wemedia.pojos.WmNews;

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
}
