package com.heima.admin.controller.v1;

import com.heima.admin.LoginControllerApi;
import com.heima.admin.service.AdUserService;
import com.heima.model.admin.dtos.AdUserDto;
import com.heima.model.common.dtos.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: tang
 * @date: Create in 18:45 2021/8/29
 * @description:
 */
@RestController
@RequestMapping("login")
public class LoginController implements LoginControllerApi {

    @Autowired
    private AdUserService adUserService;

    @PostMapping("in")
    @Override
    public ResponseResult login(@RequestBody AdUserDto dto) {
        return adUserService.login(dto);
    }
}
