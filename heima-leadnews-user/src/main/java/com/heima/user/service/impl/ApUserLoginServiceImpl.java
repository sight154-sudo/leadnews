package com.heima.user.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.heima.common.constants.message.FollowBehaviorConstants;
import com.heima.common.constants.message.UserLoginMessageConstants;
import com.heima.common.exception.SystemException;
import com.heima.model.behavior.pojos.ApBehaviorEntry;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.user.dtos.LoginDto;
import com.heima.model.user.pojos.ApUser;
import com.heima.user.mapper.ApUserMapper;
import com.heima.user.service.ApUserLoginService;
import com.heima.utils.common.AppJwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: tang
 * @date: Create in 10:04 2021/9/11
 * @description:
 */
@Service
public class ApUserLoginServiceImpl implements ApUserLoginService {

    @Autowired
    private ApUserMapper apUserMapper;

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    @Override
    public ResponseResult login(LoginDto dto) {
        //校验参数
        if(ObjectUtil.isEmpty(dto)){
            return ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.PARAM_INVALID);
        }
        String token = "";
        Map<String, Object> map = new HashMap<>();
        Short type = null;
        ApBehaviorEntry apBehaviorEntry = new ApBehaviorEntry();
        //手机号密码登陆
        if(StrUtil.isNotBlank(dto.getPhone()) && StrUtil.isNotBlank(dto.getPassword())){
            ApUser apUser = apUserMapper.findByPhone(dto.getPhone());
            if(ObjectUtil.isNotEmpty(apUser)) {
                String pwd = DigestUtils.md5DigestAsHex((dto.getPassword() + apUser.getSalt()).getBytes());
                if (StrUtil.equals(pwd, apUser.getPassword())) {
                    //密码一致 生成用户token
                    token = AppJwtUtil.getToken(Convert.toLong(apUser.getId()));
                    if (StrUtil.isNotBlank(token)) {
                        //返回用户信息及token给客户端
                        map.put("token", token);
                    } else {
                        throw new SystemException("token生成失败");
                    }
                }
            }else{
                return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
            }
            type = 1;
            apBehaviorEntry.setEntryId(apUser.getId());
        }
        //设备登陆 根据设备id生成token返回给客户端
        if(StrUtil.isNotBlank(dto.getEquipmentId()) && StrUtil.isBlank(dto.getPassword()) && StrUtil.isBlank(dto.getPhone())){
            token = AppJwtUtil.getToken(0L);
            map.put("token",token);
            type = 0;
            apBehaviorEntry.setEntryId(Convert.toInt(dto.getEquipmentId()));
        }
        //结果返回
        //TODO 用户登陆成功后，发送消息添加用户行为信息
        if(null != type){
            apBehaviorEntry.setType(type);
            kafkaTemplate.send(UserLoginMessageConstants.USER_LOGIN_MESSAGE, JSON.toJSONString(apBehaviorEntry));
        }
        return ResponseResult.okResult(map);
    }
}
