package com.heima.model.behavior.pojos;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * APP不喜欢行为表
 * </p>
 *
 * @author itheima
 */
@Data
public class ApUnlikesBehavior implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 实体ID
     */
    private Integer entryId;

    /**
     * 文章ID
     */
    private Long articleId;

    /**
     * 0 不喜欢
     1 取消不喜欢
     */
    private Integer type;

    /**
     * 登录时间
     */
    private Date createdTime;
}