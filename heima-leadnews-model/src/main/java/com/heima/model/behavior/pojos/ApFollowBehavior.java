package com.heima.model.behavior.pojos;

import com.heima.model.common.annotation.IdEncrypt;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * APP关注行为表
 * </p>
 *
 * @author itheima
 */
@Data
public class ApFollowBehavior implements Serializable {

    private static final long serialVersionUID = 1L;

    @IdEncrypt
    private Long id;

    /**
     * 实体ID
     */
    private Integer entryId;

    /**
     * 文章ID
     */
    @IdEncrypt
    private Long articleId;

    /**
     * 关注用户ID
     */
    private Integer followId;

    /**
     * 登录时间
     */
    private Date createdTime;

}