package com.heima.wemedia;

import com.heima.model.common.dtos.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: tang
 * @date: Create in 19:09 2021/9/3
 * @description:
 */
@Api(value = "自媒体后台管理",tags = "wmMaterial")
public interface WmMaterialControllerApi {

    /**
     * 用户上传图片
     * @param file
     * @return
     */
    @ApiOperation("用户上传图片")
    public ResponseResult uploadPic(MultipartFile[] file);
}
