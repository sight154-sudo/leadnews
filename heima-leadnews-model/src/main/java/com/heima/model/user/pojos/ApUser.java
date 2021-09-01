package com.heima.model.user.pojos;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: tang
 * @date: Create in 11:38 2021/8/31
 * @description: App用户信息表
 */
@Data
public class ApUser implements Serializable {
    /**
     * 主键id
     */
    private Integer id;
    /**
     *加密盐
     */
    private String salt;
    /**
     *用户名称
     */
    private String name;
    /**
     *密码
     */
    private String password;
    /**
     *手机号
     */
    private String phone;
    /**
     *头像
     */
    private String image;
    /**
     *性别  0 男  1 女   2 未知
     */
    private Short sex;
    /**
     *是否认证  0 否   1  是
     */
    private Boolean isCertification;
    /**
     *身份认证
     */
    private Boolean isIdentityAuthentication;
    /**
     *状态   0 正常  1 锁定
     */
    private Boolean status;
    /**
     * 0 普通用户  1 自媒体人   2 大V',
     */
    private Short flag;
    /**
     *创建时间
     */
    private Date createdTime;

}
