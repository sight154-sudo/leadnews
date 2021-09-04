package com.heima.wemedia.controller.v1;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.wemedia.WmMaterialControllerApi;
import com.heima.wemedia.service.WmMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: tang
 * @date: Create in 19:12 2021/9/3
 * @description:
 */
@RestController
@RequestMapping("api/v1/material")
public class WmMaterialController implements WmMaterialControllerApi {

    @Autowired
    private WmMaterialService wmMaterialService;

    @Override
    @PostMapping("upload_picture")
    public ResponseResult uploadPic(MultipartFile[] multipartFile) {
        //api/v1/material/upload_picture
        return wmMaterialService.uploadPic(multipartFile);
    }


}
