package com.heima.admin.service;

import com.heima.model.admin.dtos.AdChannelDto;
import com.heima.model.admin.pojos.AdChannel;
import com.heima.model.common.dtos.ResponseResult;

/**
 * @author: tang
 * @date: Create in 20:16 2021/8/28
 * @description:
 */
public interface AdChannelService {

    /**
     * 根据频道名称分页查询
     * @param dto
     * @return
     */
    ResponseResult findAllByName(AdChannelDto dto);

    /**
     * 新增频道信息
     * @param adchannel
     * @return
     */
    ResponseResult save(AdChannel adchannel);

    /**
     * 修改频道信息
     * @param adChannel
     * @return
     */
    ResponseResult update(AdChannel adChannel);

    /**
     * 删除频道信息
     * @param id
     * @return
     */
    ResponseResult delete(Integer id);

    /**
     * 根据所有频道信息
     * @return
     */
    ResponseResult findAll();
}
