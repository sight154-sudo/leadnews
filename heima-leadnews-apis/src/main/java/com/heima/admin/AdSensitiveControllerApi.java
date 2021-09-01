package com.heima.admin;

import com.heima.model.admin.dtos.AdSensitiveDto;
import com.heima.model.admin.pojos.AdSensitive;
import com.heima.model.common.dtos.PageResponseResult;
import com.heima.model.common.dtos.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author: tang
 * @date: Create in 15:28 2021/8/29
 * @description:
 */
@Api(value = "敏感词管理",tags = "sensitive",description = "敏感词管理API")
public interface AdSensitiveControllerApi {
    /**
     * 根据敏感词名称查询
     * @param dto
     * @return
     */
    @ApiOperation("根据敏感词名称查询")
    ResponseResult<PageResponseResult> findAllByName(@RequestBody AdSensitiveDto dto);

    /**
     * 新增敏感词
     * @param adSensitive
     * @return
     */
    @ApiOperation("新增敏感词")
    ResponseResult saveSensitive(@RequestBody AdSensitive adSensitive);

    /**
     * 修改敏感词
     * @param adSensitive
     * @return
     */
    @ApiOperation("修改敏感词")
    ResponseResult updateSensitive(@RequestBody AdSensitive adSensitive);

    /**
     * 根据id删除敏感词
     * @param id
     * @return
     */
    @ApiOperation("根据id删除敏感词")
    ResponseResult deleteSensitiveById(Integer id);
}
