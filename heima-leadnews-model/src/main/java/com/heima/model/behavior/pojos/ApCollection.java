package com.heima.model.behavior.pojos;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * APP收藏信息表
 * </p>
 *
 * @author itheima
 */
@Data
public class ApCollection implements Serializable {

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
     * 点赞内容类型
     0文章
     1动态
     */
    private Short type;

    /**
     * 创建时间
     */
    private Date collectionTime;

    /**
     * 发布时间
     */
    private Date publishedTime;

}