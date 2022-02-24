package com.heima.model.behavior.dtos;

import lombok.Data;

/**
 * @author: tang
 * @date: Create in 9:37 2021/9/12
 * @description:
 */
@Data
public class ApFollowBehaviorDto {
    /**
     * 实体ID
     */
    private Integer entryId;

    /**
     * 文章ID
     */
    private Long articleId;

    /**
     * 关注用户ID
     */
    private Integer followId;
}
