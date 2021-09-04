package com.heima.model.wemedia.pojos;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: tang
 * @date: Create in 21:55 2021/9/3
 * @description:自媒体图文引用素材信息表
 */
@Data
public class WmNewsMaterial implements Serializable {
    /**
     * 主键id
     */
    private Integer id;
    /**
     *素材id
     */
    private Integer material_Id;
    /**
     *图文id
     */
    private Integer newsId;
    /**
     *引用类型  0 内容引用    1  主图引用
     */
    private Short type;
    /**
     *引用排序
     */
    private Integer ord;
}
