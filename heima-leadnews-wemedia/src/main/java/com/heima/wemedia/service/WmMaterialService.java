package com.heima.wemedia.service;

import com.heima.model.common.dtos.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: tang
 * @date: Create in 19:15 2021/9/3
 * @description:
 */
public interface WmMaterialService {

    /**
     * 图片上传
     * @param file
     * @return
     */
    public ResponseResult uploadPic(MultipartFile[] file);
}
