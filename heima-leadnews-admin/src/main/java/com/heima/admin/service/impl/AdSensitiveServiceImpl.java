package com.heima.admin.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.heima.admin.mapper.AdSensitiveMapper;
import com.heima.admin.service.AdSensitiveService;
import com.heima.model.admin.dtos.AdSensitiveDto;
import com.heima.model.admin.pojos.AdSensitive;
import com.heima.model.common.dtos.PageResponseResult;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author: tang
 * @date: Create in 15:37 2021/8/29
 * @description:
 */
@Service
public class AdSensitiveServiceImpl implements AdSensitiveService {

    @Autowired
    private AdSensitiveMapper adSensitiveMapper;
    @Override
    public ResponseResult findAllByName(AdSensitiveDto dto) {
        //参数校验
        if(ObjectUtil.isEmpty(dto)){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        //初始化分页参数
        dto.checkParam();
        //设置分页参数
        PageHelper.startPage(dto.getPage(),dto.getSize());
        //执行查询
        List<AdSensitive> list = adSensitiveMapper.findAll(dto.getSensitives());
        PageInfo<AdSensitive> pageInfo = new PageInfo<>(list);
        ResponseResult result = new PageResponseResult(dto.getPage(),dto.getSize(), Convert.toInt(pageInfo.getTotal()));
        result.setData(pageInfo.getList());
        return result;
    }

    @Override
    public ResponseResult save(AdSensitive sensitive) {
        if(ObjectUtil.isEmpty(sensitive)){
            return ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.PARAM_INVALID);
        }
        //设置创建时间
        sensitive.setCreatedTime(new Date());
        int res = adSensitiveMapper.save(sensitive);
        return res>0?ResponseResult.errorResult(AppHttpCodeEnum.SUCCESS):ResponseResult.errorResult(AppHttpCodeEnum.SAVE_FAILED);
    }

    @Override
    public ResponseResult update(AdSensitive adChannel) {
        if(!ObjectUtil.isAllNotEmpty(adChannel,adChannel.getId())){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        int res = adSensitiveMapper.update(adChannel);
        return res>0?ResponseResult.errorResult(AppHttpCodeEnum.SUCCESS):ResponseResult.errorResult(AppHttpCodeEnum.UPDATE_FAILED);
    }

    @Override
    public ResponseResult delete(Integer id) {
        if (ObjectUtil.isEmpty(id)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        //删除前 查询数据库中是否存在  判断状态是否有效 若有效则不能删除
        AdSensitive channel = adSensitiveMapper.findById(id);
        if (ObjectUtil.isEmpty(channel)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        int res = adSensitiveMapper.delete(id);
        return res > 0 ? ResponseResult.errorResult(AppHttpCodeEnum.SUCCESS) : ResponseResult.errorResult(AppHttpCodeEnum.DELETE_FAILED);
    }
}
