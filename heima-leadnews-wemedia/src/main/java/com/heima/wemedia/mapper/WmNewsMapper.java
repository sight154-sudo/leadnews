package com.heima.wemedia.mapper;

import com.heima.model.wemedia.dtos.WmNewsDto;
import com.heima.model.wemedia.dtos.WmNewsPageReqDto;
import com.heima.model.wemedia.pojos.WmNews;

import java.util.List;

/**
 * @author: tang
 * @date: Create in 10:22 2021/9/4
 * @description:
 */
public interface WmNewsMapper {

    /**
     * 根据条件查询所有内容列表
     * @param dto
     * @return
     */
    List<WmNews> findAll(WmNewsPageReqDto dto);

    int save(WmNews wmNews);
}
