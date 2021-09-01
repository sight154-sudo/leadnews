package com.heima.admin.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.heima.admin.mapper.AdUserMapper;
import com.heima.admin.service.AdUserService;
import com.heima.common.exception.SystemException;
import com.heima.model.admin.dtos.AdUserDto;
import com.heima.model.admin.pojos.AdUser;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.utils.common.AppJwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: tang
 * @date: Create in 18:30 2021/8/29
 * @description:
 */
@Service
public class AdUserServiceImpl implements AdUserService {

    @Autowired
    private AdUserMapper adUserMapper;

    @Override
    public ResponseResult login(AdUserDto dto) {
        //参数校验
        if(!ObjectUtil.isAllNotEmpty(dto,dto.getName(),dto.getPassword())){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        //根据用户名查询用户信息
        AdUser adUser = adUserMapper.findUserByName(dto.getName());
        if(ObjectUtil.isEmpty(adUser)){
            return ResponseResult.errorResult(AppHttpCodeEnum.AP_USER_DATA_NOT_EXIST);
        }
        //比较密码
        String pwd = DigestUtils.md5DigestAsHex((dto.getPassword()+adUser.getSalt()).getBytes());
        if(StrUtil.equals(pwd,adUser.getPassword())){
            //密码一致 生成用户token
            String token = AppJwtUtil.getToken(Convert.toLong(adUser.getId()));
            if(StrUtil.isNotBlank(token)){
                //返回用户信息及token给客户端
                Map<String,Object> map = new HashMap<>();
                adUser.setPassword("");
                adUser.setSalt("");
                map.put("user",adUser);
                map.put("token",token);

                return ResponseResult.okResult(map);
            }else{
                throw new SystemException("token生成失败");
            }
        }
        return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_PASSWORD_ERROR);
    }
}
