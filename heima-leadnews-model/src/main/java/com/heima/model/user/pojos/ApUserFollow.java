package com.heima.model.user.pojos;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: tang
 * @date: Create in 13:50 2021/9/11
 * @description:
 */
@Data
public class ApUserFollow implements Serializable {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 被关注作者的用户id
     */
    private Integer followId;
    /**
     * 被关注作者的名称
     */
    private String followName;
    /**
     *关注度 0 偶尔感兴趣 1 一般   2 经常   3 高度
     */
    private Short level;
    /**
     *是否通知动态
     */
    private Boolean isNotice;
    /**
     *创建时间
     */
    private Date createdTime;
}
