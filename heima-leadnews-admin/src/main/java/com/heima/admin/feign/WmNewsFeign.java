package com.heima.admin.feign;

import com.heima.model.admin.dtos.NewsAuthDto;
import com.heima.model.common.dtos.PageResponseResult;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.pojos.WmNews;
import com.heima.model.wemedia.pojos.WmUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

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

    /**
     * 查询需要发布的文章Id
     * @return
     */
    @GetMapping("/api/v1/news/findRelease")
    public List<Integer> findRelease();

    /**
     * 查询文章详情信息包含作者信息
     * @param dto
     * @return
     */
    @PostMapping("api/v1/news/findList")
    public PageResponseResult findListAndAuthorName(NewsAuthDto dto);

    /**
     * 查询单个审核文章的详情
     * @param id
     * @return
     */
    @GetMapping("api/v1/news/findOneAndAuthorName/{id}")
    ResponseResult findNewsAndAuthorNameById(@PathVariable Integer id);

    /**
     * 修改文章状态信息
     * @param dto
     * @return
     */
    @PostMapping("api/v1/news/updateStatus")
    public ResponseResult updateNewsStatus(NewsAuthDto dto);
}
