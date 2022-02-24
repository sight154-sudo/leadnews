package com.heima.model.user.dtos;

import lombok.Data;

/**
 * @author: tang
 * @date: Create in 19:23 2021/9/11
 * @description:
 */
@Data
public class UserRelationDto{
    /**
     * 文章id
     */
    private Long articleId;
    /**
     * 作者Id
     */
    private Integer authorId;
    /**
     * 关注 或 取消关注  0关注  1取消关注
     */
    private Integer operation;
}
