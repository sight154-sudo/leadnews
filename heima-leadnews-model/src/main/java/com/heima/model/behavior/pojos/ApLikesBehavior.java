package com.heima.model.behavior.pojos;

import com.heima.model.common.annotation.IdEncrypt;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * APP点赞行为表
 * </p>
 *
 * @author itheima
 */
@Data
public class ApLikesBehavior implements Serializable {

    private static final long serialVersionUID = 1L;

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
     * 点赞内容类型
     * 0文章
     * 1动态
     */
    private Short type;

    /**
     * 0 点赞
     * 1 取消点赞
     */
    private Short operation;

    /**
     * 登录时间
     */
    private Date createdTime;
}