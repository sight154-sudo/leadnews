package com.heima.wemedia.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.wemedia.pojos.WmUser;
import com.heima.wemedia.mapper.WmUserMapper;
import com.heima.wemedia.service.WmUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author: tang
 * @date: Create in 21:40 2021/8/30
 * @description:
 */
@Service
public class WmUserServiceImpl implements WmUserService {

    @Autowired
    private WmUserMapper wmUserMapper;

    @Override
    public ResponseResult save(WmUser wmUser) {
        if(ObjectUtil.isEmpty(wmUser)){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        wmUser.setCreated_time(new Date());
        int res = wmUserMapper.save(wmUser);
        return res>0?ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.SUCCESS):ResponseResult.errorResult(AppHttpCodeEnum.SAVE_FAILED);
    }

    @Override
    public WmUser findByName(String name) {
        if(StrUtil.isNotBlank(name)){
            return wmUserMapper.findByName(name);
        }
        return null;
    }
}
