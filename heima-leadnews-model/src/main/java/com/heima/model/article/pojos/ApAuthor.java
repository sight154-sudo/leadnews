package com.heima.model.article.pojos;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: tang
 * @date: Create in 22:08 2021/8/30
 * @description: app文章作者信息
 */
@Data
public class ApAuthor implements Serializable {
    /**
     * 主键id
     */
    private Integer id;
    /**
     * 作者名称
     */
    private String name;
    /**
     *数据类型  0  爬取数据   1  签约合作商   2  平台自媒体人
     */
    private Integer type;
    /**
     *社交账号id
     */
    private Integer userId;
    /**
     *创建时间
     */
    private Date createdTime;
    /**
     *自媒体账号
     */
    private Integer wmUserId;
}
