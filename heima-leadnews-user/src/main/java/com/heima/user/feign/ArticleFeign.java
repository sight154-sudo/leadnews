package com.heima.user.feign;

import com.heima.model.article.pojos.ApAuthor;
import com.heima.model.common.dtos.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author: tang
 * @date: Create in 18:52 2021/8/31
 * @description:
 */
@FeignClient("leadnews-article")
public interface ArticleFeign {
    /**
     * 根据用户id查询作者信息
     * @param id
     * @return
     */
    @GetMapping("/api/v1/author/findById/{id}")
    ApAuthor findById(@PathVariable Integer id);

    /**
     * 保存作者信息
     * @param apAuthor
     * @return
     */
    @PostMapping("/api/v1/author/save")
    ResponseResult save(@RequestBody ApAuthor apAuthor);
}
