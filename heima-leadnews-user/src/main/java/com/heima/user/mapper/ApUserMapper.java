package com.heima.user.mapper;

import com.heima.model.user.pojos.ApUser;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author: tang
 * @date: Create in 18:57 2021/8/31
 * @description:
 */
public interface ApUserMapper {

    /**
     * 根据id查询用户信息
     * @param id
     * @return
     */
    @Select("select id,salt,name,password,phone,image,sex,is_certification isCertification," +
            "is_identity_authentication isIdentityAuthentication,status,flag,created_time createdTime" +
            " from ap_user where id = #{id}")
    ApUser findById(Integer id);


    /**
     * 根据手机号查询用户信息
     * @param phone
     * @return
     */
    @Select("select id,salt,name,password,phone,image,sex,is_certification isCertification," +
            "is_identity_authentication isIdentityAuthentication,status,flag,created_time createdTime" +
            " from ap_user where phone = #{phone}")
    ApUser findByPhone(String phone);

    /**
     * 修改用户所属分类
     * @param apUser
     * @return
     */
    @Update("update ap_user set flag = #{flag} where id = #{id}")
    int updateUserStatus(ApUser apUser);
}
