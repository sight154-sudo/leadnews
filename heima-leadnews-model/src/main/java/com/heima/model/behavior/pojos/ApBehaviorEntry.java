package com.heima.model.behavior.pojos;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * APP行为实体表,一个行为实体可能是用户或者设备，或者其它
 * </p>
 *
 * @author itheima
 */
@Data
public class ApBehaviorEntry implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 实体类型
     0终端设备
     1用户
     */
    private Short type;

    /**
     * 实体ID
     */
    private Integer entryId;

    /**
     * 创建时间
     */
    private Date createdTime;

}