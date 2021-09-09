package com.heima.article.service;

import com.heima.model.article.pojos.ApAuthor;
import com.heima.model.common.dtos.ResponseResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author: tang
 * @date: Create in 22:15 2021/8/30
 * @description:
 */
public interface ApAuthorService {
    /**
     *根据用户id查询作者信息
     * @param id
     * @return
     */
    ApAuthor findByUserId(Integer id);

    /**
     * 保存作者
     * @param apAuthor
     * @return
     */
    ResponseResult save(ApAuthor apAuthor);

    /**
     * 通过name查询文章作者信息
     * @param name
     * @return
     */
    ApAuthor findByName(String name);
}
