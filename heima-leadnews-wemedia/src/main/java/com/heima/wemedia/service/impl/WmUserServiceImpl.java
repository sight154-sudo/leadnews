package com.heima.wemedia.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.heima.common.exception.SystemException;
import com.heima.model.admin.pojos.AdUser;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.wemedia.dtos.WmUserDto;
import com.heima.model.wemedia.pojos.WmUser;
import com.heima.utils.common.AppJwtUtil;
import com.heima.wemedia.mapper.WmUserMapper;
import com.heima.wemedia.service.WmUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
    public ResponseResult login(WmUserDto dto) {
        //参数校验
        if(!ObjectUtil.isAllNotEmpty(dto,dto.getName(),dto.getPassword())){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        //根据用户名查询用户信息
        WmUser wmUser = wmUserMapper.findUserByName(dto.getName());
        if(ObjectUtil.isEmpty(wmUser)){
            return ResponseResult.errorResult(AppHttpCodeEnum.AP_USER_DATA_NOT_EXIST);
        }
        //比较密码
        String pwd = DigestUtils.md5DigestAsHex((dto.getPassword()+wmUser.getSalt()).getBytes());
        if(StrUtil.equals(pwd,wmUser.getPassword())){
            //密码一致 生成用户token
            String token = AppJwtUtil.getToken(Convert.toLong(wmUser.getId()));
            if(StrUtil.isNotBlank(token)){
                //返回用户信息及token给客户端
                Map<String,Object> map = new HashMap<>();
                wmUser.setPassword("");
                wmUser.setSalt("");
                map.put("user",wmUser);
                map.put("token",token);
                return ResponseResult.okResult(map);
            }else{
                throw new SystemException("token生成失败");
            }
        }
        return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_PASSWORD_ERROR);
    }

    @Override
    @Transactional
    public ResponseResult save(WmUser wmUser) {
        if(ObjectUtil.isEmpty(wmUser)){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        wmUser.setCreatedTime(DateUtil.now());
        int res = wmUserMapper.save(wmUser);
//        int i = 1/0;
        return res!=0?ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.SUCCESS):ResponseResult.errorResult(AppHttpCodeEnum.SAVE_FAILED);
    }

    @Override
    public WmUser findByName(String name) {
        if(StrUtil.isNotBlank(name)){
            return wmUserMapper.findByName(name);
        }
        return null;
    }


}
