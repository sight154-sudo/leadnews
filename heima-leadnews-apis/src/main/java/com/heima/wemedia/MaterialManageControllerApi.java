package com.heima.wemedia;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.WmMaterialDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author: tang
 * @date: Create in 20:44 2021/9/3
 * @description:
 */
@Api(value = "素材管理",tags = "MaterialManage")
public interface MaterialManageControllerApi {

    /**
     * 素材管理列表
     * @param dto
     * @return
     */
    @ApiOperation("素材管理列表")
    public ResponseResult findAll(WmMaterialDto dto);

    /**
     * 根据图片id删除图片
     * @param id
     * @return
     */
    @ApiOperation("根据图片id删除图片")
    public ResponseResult delPic(Integer id);


    /**
     * 取消图片收藏
     * @param id
     * @return
     */
    @ApiOperation("取消图片收藏")
    public ResponseResult cancleCollectionMaterial(Integer id);

    /**
     * 收藏图片
     * @param id
     * @return
     */
    public ResponseResult collectionMaterial(Integer id);
}
