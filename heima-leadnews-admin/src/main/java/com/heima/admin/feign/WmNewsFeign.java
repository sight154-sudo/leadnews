package com.heima.admin.feign;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.pojos.WmNews;
import com.heima.model.wemedia.pojos.WmUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author: tang
 * @date: Create in 21:26 2021/9/6
 * @description:
 */
@FeignClient("leadnews-wemedia")
public interface WmNewsFeign {

    /**
     * 通过id查询文章信息
     * @param id
     * @return
     */
    @GetMapping("/api/v1/news/findOne/{id}")
    public WmNews findById(@PathVariable("id") Integer id);

    /**
     * 修改文章状态
     * @param wmNews
     * @return
     */
    @PostMapping("/api/v1/news/update")
    public ResponseResult updateWmNews(WmNews wmNews);

    /**
     * 通过id查询自媒体用户信息
     * @param id
     * @return
     */
    @GetMapping("/api/v1/user/findOne/{id}")
    public WmUser findWmUserById(@PathVariable Integer id);


}
