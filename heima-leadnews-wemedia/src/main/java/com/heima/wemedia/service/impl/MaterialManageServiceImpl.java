package com.heima.wemedia.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.heima.common.fastdfs.FastDFSClient;
import com.heima.model.common.dtos.PageResponseResult;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.wemedia.dtos.WmMaterialDto;
import com.heima.model.wemedia.pojos.WmMaterial;
import com.heima.model.wemedia.pojos.WmNewsMaterial;
import com.heima.model.wemedia.pojos.WmUser;
import com.heima.utils.threadlocal.WmThreadLocalUtils;
import com.heima.wemedia.mapper.WmMaterialMapper;
import com.heima.wemedia.mapper.WmNewsMaterialMapper;
import com.heima.wemedia.service.MaterialManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: tang
 * @date: Create in 20:56 2021/9/3
 * @description:
 */
@Service
public class MaterialManageServiceImpl implements MaterialManageService {

    @Autowired
    private WmMaterialMapper wmMaterialMapper;

    @Autowired
    private WmNewsMaterialMapper wmNewsMaterialMapper;

    @Autowired
    private FastDFSClient fastDFSClient;

    @Value("${fdfs.url}")
    private String prefixUrl;

    /**
     * 查询素材列表
     * @param dto
     * @return
     */
    @Override
    public ResponseResult findAll(WmMaterialDto dto) {
        if(!ObjectUtil.isAllNotEmpty(dto,dto.getIsCollection())){
            return ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.PARAM_INVALID);
        }
        //初始化分页参数
        dto.checkParam();
        WmUser wmUser = WmThreadLocalUtils.get();
        if(ObjectUtil.isEmpty(wmUser)){
            return ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.UNAUTHORIZED);
        }
        dto.setUserId(wmUser.getId());
        PageHelper.startPage(dto.getPage(),dto.getSize());
        List<WmMaterial> list = wmMaterialMapper.findAll(dto);
        PageInfo<WmMaterial> pageInfo = new PageInfo<>(list);
        ResponseResult result = new PageResponseResult(pageInfo.getPageNum(),pageInfo.getSize(), Convert.toInt(pageInfo.getTotal()));
        List<WmMaterial> data = pageInfo.getList().stream().map(wm -> {
            wm.setUrl(prefixUrl + wm.getUrl());
            return wm;
        }).collect(Collectors.toList());
        result.setData(data);
        return result;
    }

    /**
     * 删除图片
     * @param id
     * @return
     */
    @Override
    public ResponseResult delPic(Integer id) {
        if(ObjectUtil.isEmpty(id)){
            return ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.PARAM_INVALID);
        }
        WmUser wmUser = WmThreadLocalUtils.get();
        if(ObjectUtil.isEmpty(wmUser)){
            return ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.UNAUTHORIZED);
        }
        //如果图片被引用则不能删除
        WmNewsMaterial wmNewsMaterial = wmNewsMaterialMapper.findById(id);
        if(ObjectUtil.isNotEmpty(wmNewsMaterial)){
            return ResponseResult.okResult(500,"图片正在使用中不能删除");
        }
        WmMaterial wmMaterial = wmMaterialMapper.findById(id);
        if(ObjectUtil.isEmpty(wmMaterial)){
            return ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.PARAM_INVALID);
        }
        fastDFSClient.delFile(wmMaterial.getUrl());
        int res = wmMaterialMapper.delPic(id);
        return res!=0?ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.SUCCESS):ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.FAILED);
    }

    @Override
    public ResponseResult updateIsCollection(Integer id, Boolean isCollection) {
        if(null == id || null == isCollection){
            return ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.PARAM_INVALID);
        }
        WmMaterial wmMaterial = new WmMaterial();
        wmMaterial.setId(id);
        wmMaterial.setIsCollection(isCollection);
        int res =wmMaterialMapper.updateIsCollection(wmMaterial);
        return res!=0?ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.SUCCESS):ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.FAILED);
    }
}
