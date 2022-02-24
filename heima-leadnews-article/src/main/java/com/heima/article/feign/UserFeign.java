package com.heima.article.feign;

import com.heima.model.user.pojos.ApUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author: tang
 * @date: Create in 21:51 2021/9/12
 * @description:
 */
@FeignClient("leadnews-user")
public interface UserFeign {

    /**
     *
     * @param userId
     * @param followId
     * @return
     */
    @GetMapping("api/v1/user/isFollow")
    Boolean isFollow(@RequestParam("userId") Integer userId, @RequestParam("followId") Integer followId);

    /**
     *
     * @param userId
     * @return
     */
    @GetMapping("api/v1/user/findOne")
    public ApUser findUserById(@RequestParam("userId") Integer userId);
}
