package com.heima.user.controller.v1;

import com.heima.model.user.pojos.ApUser;
import com.heima.user.ApUserControllerApi;
import com.heima.user.service.ApUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: tang
 * @date: Create in 19:55 2021/9/13
 * @description:
 */
@RestController
@RequestMapping("api/v1/user")
public class ApUserController implements ApUserControllerApi {

    @Autowired
    private ApUserService apUserService;

    @GetMapping("findOne")
    @Override
    public ApUser findUserById(@RequestParam("userId") Integer userId) {
        return apUserService.findUserById(userId);
    }
}
