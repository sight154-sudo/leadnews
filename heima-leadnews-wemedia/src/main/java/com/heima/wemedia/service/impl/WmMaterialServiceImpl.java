package com.heima.wemedia.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.heima.common.fastdfs.FastDFSClient;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.wemedia.pojos.WmMaterial;
import com.heima.model.wemedia.pojos.WmUser;
import com.heima.utils.threadlocal.WmThreadLocalUtils;
import com.heima.wemedia.mapper.WmMaterialMapper;
import com.heima.wemedia.service.WmMaterialService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: tang
 * @date: Create in 19:15 2021/9/3
 * @description:
 */
@Service
@Slf4j
public class WmMaterialServiceImpl implements WmMaterialService {

    @Autowired
    private FastDFSClient fastDFSClient;
    @Autowired
    private WmMaterialMapper wmMaterialMapper;

    @Value("${fdfs.url}")
    private String prefixUrl;


    @Override
    public ResponseResult uploadPic(MultipartFile[] file) {
        int res = 0;
        try {
            if (ObjectUtil.isEmpty(file) || file.length == 0) {
                return ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.PARAM_REQUIRE);
            }
            WmUser wmUser = WmThreadLocalUtils.get();
            if (ObjectUtil.isEmpty(wmUser)) {
                return ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.UNAUTHORIZED);
            }
            //上传图片到文件系统中
            String url = "";
            for (MultipartFile multipartFile : file) {
                url += fastDFSClient.uploadFile(multipartFile);
            }
            WmMaterial wmMaterial = new WmMaterial();
            wmMaterial.setUrl(url);
            wmMaterial.setUserId(wmUser.getId());
            wmMaterial.setCreatedTime(new Date());
            wmMaterial.setIsCollection(false);
            wmMaterial.setType(0);
            res = wmMaterialMapper.save(wmMaterial);
            Map<String,String> map = new HashMap<>();
            map.put("url",prefixUrl+wmMaterial.getUrl());
            return res>0?ResponseResult.okResult(map):ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.FAILED);
        } catch (Exception e) {
           log.error("上传图片失败，userId={}",WmThreadLocalUtils.get().getId(),e);
        }
        return ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.FAILED);
    }
}
