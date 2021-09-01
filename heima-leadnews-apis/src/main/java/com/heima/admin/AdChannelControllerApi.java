package com.heima.admin;

import com.heima.model.admin.dtos.AdChannelDto;
import com.heima.model.admin.pojos.AdChannel;
import com.heima.model.common.dtos.PageResponseResult;
import com.heima.model.common.dtos.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;

/**
 * @author: tang
 * @date: Create in 19:59 2021/8/28
 * @description:
 */
@Api(value = "频道管理",tags = "channel",description = "频道管理API")
public interface AdChannelControllerApi {

    /**
     * 根据频道名称分页查询出频道数据
     * @param dto
     * @return
     */
    @ApiOperation(value = "根据频道名称分页查询出频道数据",response = ResponseResult.class)
    @ApiResponse(code = 200,message = "响应频道分页数据")
    ResponseResult<PageResponseResult> findAllByName(AdChannelDto dto);

    /**
     * 新增频道信息
     * @param adChannel
     * @return
     */
    @ApiOperation("新增频道信息")
    ResponseResult saveChannel(AdChannel adChannel);

    /**
     * 修改频道信息
     * @param adChannel
     * @return
     */
    @ApiOperation("修改频道信息")
    ResponseResult updateChannel(AdChannel adChannel);

    /**
     * 根据id删除频道信息
     * @param id
     * @return
     */
    @ApiOperation("根据id删除频道信息")
    ResponseResult deleteChannel(Integer id);
}
