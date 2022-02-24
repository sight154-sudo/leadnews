package com.heima.user.service.impl;

import com.heima.model.user.pojos.ApUser;
import com.heima.user.mapper.ApUserMapper;
import com.heima.user.service.ApUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: tang
 * @date: Create in 19:57 2021/9/13
 * @description:
 */
@Service
public class ApUserServiceImpl implements ApUserService {

    @Autowired
    private ApUserMapper apUserMapper;

    @Override
    public ApUser findUserById(Integer userId) {
        return apUserMapper.findById(userId);
    }
}
