package com.heima.model.admin.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: tang
 * @date: Create in 19:35 2021/8/28
 * @description: 频道实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdChannel implements Serializable {

    /**
     * 主键id
     */
    private Integer id;
    private String name;//频道名称
    private String description;//频道描述
    private Boolean isDefault;//是否为默认频道
    private Boolean status;
    private Integer ord;
    private Date createTime;
}
