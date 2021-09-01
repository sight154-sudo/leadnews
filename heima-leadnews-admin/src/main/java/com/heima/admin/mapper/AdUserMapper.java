package com.heima.admin.mapper;

import com.heima.model.admin.pojos.AdUser;
import org.apache.ibatis.annotations.Select;

/**
 * @author: tang
 * @date: Create in 18:26 2021/8/29
 * @description:
 */
public interface AdUserMapper {
    /**
     * 根据用户名查询用户信息
     * @param name
     * @return
     */
    @Select("select id,name,password,salt,nickname,image,phone,status,email,login_time LoginTime,created_time createdTime from ad_user where name = #{name}")
    AdUser findUserByName(String name);
}
