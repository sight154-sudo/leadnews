package com.heima.model.article.dtos;

import lombok.Data;

import java.util.Date;

/**
 * @author: tang
 * @date: Create in 10:10 2021/9/10
 * @description:
 */
@Data
public class ArticleHomeDto {
    /**
     *最大时间
     */
    Date maxBehotTime;
    /**
     * 最小时间
     */
    Date minBehotTime;
    /**
     * 分页size
     */
    Integer size;
    /**
     * 数据范围，比如频道ID
     */
    String tag;
}
