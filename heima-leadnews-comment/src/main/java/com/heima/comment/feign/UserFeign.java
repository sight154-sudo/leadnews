package com.heima.comment.feign;

import com.heima.model.user.pojos.ApUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author: tang
 * @date: Create in 19:52 2021/9/13
 * @description:
 */
@FeignClient("leadnews-user")
public interface UserFeign {
    /**
     * 通过id查询用户信息
     * @param userId
     * @return
     */
    @GetMapping("api/v1/user/findOne")
    public ApUser findUserById(@RequestParam("userId") Integer userId);
}
