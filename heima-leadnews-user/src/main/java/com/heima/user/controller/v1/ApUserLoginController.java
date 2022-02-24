package com.heima.user.controller.v1;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.user.dtos.LoginDto;
import com.heima.user.ApUserLoginControllerApi;
import com.heima.user.service.ApUserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: tang
 * @date: Create in 10:03 2021/9/11
 * @description:
 */
@RestController
@RequestMapping("api/v1/login")
public class ApUserLoginController implements ApUserLoginControllerApi {


    @Autowired
    private ApUserLoginService apUserLoginService;

    /**
     * 用户登陆
     * @param dto
     * @return
     */
    @PostMapping("login_auth")
    @Override
    public ResponseResult login(@RequestBody LoginDto dto) {
        return apUserLoginService.login(dto);
    }
}
