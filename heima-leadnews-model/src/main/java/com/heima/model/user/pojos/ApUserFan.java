package com.heima.model.user.pojos;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * APP用户粉丝信息表
 * </p>
 * @author itheima
 */
@Data
public class ApUserFan implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 粉丝ID
     */
    private Integer fansId;

    /**
     * 粉丝昵称
     */
    private String fansName;

    /**
     * 粉丝忠实度
            0 正常
            1 潜力股
            2 勇士
            3 铁杆
            4 老铁
     */
    private Short level;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 是否可见我动态
     */
    private Boolean isDisplay;

    /**
     * 是否屏蔽私信
     */
    private Boolean isShieldLetter;

    /**
     * 是否屏蔽评论
     */
    private Boolean isShieldComment;

}