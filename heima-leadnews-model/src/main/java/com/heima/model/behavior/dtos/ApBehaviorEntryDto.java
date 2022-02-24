package com.heima.model.behavior.dtos;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: tang
 * @date: Create in 20:33 2021/9/12
 * @description:
 */
@Data
public class ApBehaviorEntryDto implements Serializable {
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
}
