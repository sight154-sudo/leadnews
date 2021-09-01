package com.heima.model.admin.pojos;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: tang
 * @date: Create in 18:13 2021/8/29
 * @description: 管理员用户信息实体表
 */
@Data
public class AdUser implements Serializable {
    private Integer id;
    private String name;//登录用户名
    private String password;//登录密码
    private String salt;//盐
    private String nickname;//昵称
    private String image;//头像
    private String phone;//手机号
    private Short status;//状态
    private String email;//邮箱
    private Date loginTime;//最后一次登陆时间
    private Date createdTime;//创建时间
}
