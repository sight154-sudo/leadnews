package com.heima.model.wemedia.pojos;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: tang
 * @date: Create in 21:15 2021/8/30
 * @description: 自媒体人用户表
 */
@Data
public class WmUser implements Serializable {
    private Integer id;
    /**
     * app用户id
     */
    private Integer apUserId;
    /**
     * 作者id
     */
    private Integer apAuthorId;
    /**
     * 登陆用户名
     */
    private String name;
    /**
     *密码
     */
    private String password;
    /**
     *盐
     */
    private String salt;
    /**
     *呢称
     */
    private String nickname;
    /**
     *用户头像
     */
    private String image;
    /**
     *归属地
     */
    private String location;
    /**
     *手机号
     */
    private String phone;
    /**
     *状态  0 暂不可用   1 永久不可用   9正常
     */
    private Integer status;
    /**
     *邮箱
     */
    private String email;
    /**
     *账号类型   0 个人   1 企业   2 子账号
     */
    private Integer type;
    /**
     *运营评分
     */
    private Integer score;
    /**
     *最后一次登陆时间
     */
    private Date login_time;
    /**
     *创建时间
     */
    private Date created_time;
}
