package com.heima.model.wemedia.pojos;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: tang
 * @date: Create in 21:55 2021/9/2
 * @description:
 */
@Data
public class WmMaterial implements Serializable {
    private Integer id;
    /**
     * 自媒体用户id
     */
    private Integer userId;
    /**
     *图片访问地址
     */
    private String url;
    /**
     *素材类型    0 图片   1 视频
     */
    private Integer type;
    /**
     * 是否收藏
     */
    private Boolean isCollection;
    /**
     * 创建时间
     */
    private Date createdTime;
}
