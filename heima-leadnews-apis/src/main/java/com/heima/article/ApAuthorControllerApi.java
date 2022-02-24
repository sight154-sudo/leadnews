package com.heima.article;

import com.heima.model.article.pojos.ApArticle;
import com.heima.model.article.pojos.ApAuthor;
import com.heima.model.common.dtos.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author: tang
 * @date: Create in 22:13 2021/8/30
 * @description:
 */
@Api(value = "文章作者管理",tags = "author")
public interface ApAuthorControllerApi {
    /**
     *根据用户id查询作者信息
     * @param id
     * @return
     */
    @ApiOperation("根据用户id查询作者信息")
    public ApAuthor findById(@PathVariable("id") Integer id);

    /**
     * 保存作者
     * @param apAuthor
     * @return
     */
    @ApiOperation("保存作者")
    public ResponseResult save(@RequestBody ApAuthor apAuthor);


    /**
     * 查询文章作者信息
     * @param name
     * @return
     */
    @ApiOperation("通过name查询文章作者信息")
    ApAuthor selectAuthorByName(@PathVariable("name") String name);
}
