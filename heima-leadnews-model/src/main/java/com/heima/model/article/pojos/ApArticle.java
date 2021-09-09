package com.heima.model.article.pojos;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: tang
 * @date: Create in 21:09 2021/9/6
 * @description: 文章详情表
 */
@Data
public class ApArticle implements Serializable {
    private Long id;
    /**
     * 文章标题
     */
    private String title;
    /**
     * 作者id
     */
    private Integer authorId;
    /**
     * 文章作者名称
     */
    private String authorName;
    /**
     * 频道id
     */
    private Integer channelId;
    /**
     * 频道内容
     */
    private String channelName;
    /**
     * 0 无图   1 单图   2 多图
     */
    private Short layout;
    /**
     *文章标记 0 普通文章 1 热点文章 2 置顶文章  3 精品文章  4 大V 文章',
     */
    private Short flag;
    /**
     * 文章图片
     */
    private String images;
    /**
     * 文章标签最多3个 逗号分隔
     */
    private String labels;
    /**
     * 点赞数
     */
    private Integer likes;
    /**
     * 收藏数
     */
    private Integer collection;
    /**
     * 评论数
     */
    private Integer comment;
    /**
     * 阅读数
     */
    private Integer views;
    /**
     * 省市id
     */
    private Integer provinceId;
    /**
     * 城市id
     */
    private Integer cityId;
    /**
     * 区县Id
     */
    private Integer countyId;
    /**
     * 创建时间
     */
    private Date createdTime;
    /**
     * 发布时间
     */
    private Date publishTime;
    /**
     * 同步状态
     */
    private Boolean syncStatus;
    /**
     * 来源
     */
    private Integer origin;
}
