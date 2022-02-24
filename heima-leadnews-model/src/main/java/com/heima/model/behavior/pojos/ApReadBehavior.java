package com.heima.model.behavior.pojos;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author king
 */
@Data
public class ApReadBehavior implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 用户ID
     */
    private Integer entryId;

    /**
     * 文章ID
     */
    private Long articleId;

    /**
     * 阅读次数
     */
    private Short count;

    /**
     * 阅读时间单位秒
     */
    private Integer readDuration;

    /**
     * 阅读百分比
     */
    private Short percentage;

    /**
     * 文章加载时间
     */
    private Short loadDuration;

    /**
     * 登录时间
     */
    private Date createdTime;

    /**
     * 更新时间
     */
    private Date updatedTime;

}