package com.heima.user;

import com.heima.model.user.pojos.ApUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author: tang
 * @date: Create in 19:54 2021/9/13
 * @description:
 */
public interface ApUserControllerApi {
    /**
     * 通过id查询用户信息
     * @param userId
     * @return
     */
    public ApUser findUserById(@RequestParam("userId") Integer userId);
}
