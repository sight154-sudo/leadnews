package com.heima.model.user.dtos;

import lombok.Data;

/**
 * @author: tang
 * @date: Create in 10:01 2021/9/11
 * @description:
 */
@Data
public class LoginDto {
    /**
     * 设备id
     */
    private String equipmentId;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 用户手机号
     */
    private String phone;
}
