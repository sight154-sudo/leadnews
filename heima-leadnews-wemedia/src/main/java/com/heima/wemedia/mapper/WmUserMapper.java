package com.heima.wemedia.mapper;

import com.heima.model.wemedia.pojos.WmUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author: tang
 * @date: Create in 21:39 2021/8/30
 * @description:
 */
public interface WmUserMapper {

    /**
     * 保存自媒体用户
     * @param wmUser
     * @return
     */
    int save(WmUser wmUser);

    /**
     * 根据自媒体用户名查询
     * @param name
     * @return
     */
    @Select("select id,ap_user_id apUserId,ap_author_id apAuthorId,name,password,salt,nickname,image,location,phone,status,email,type,score,login_time loginTime,created_time createdTime from wm_user where name = #{name}")
    WmUser findByName(String name);

    /**
     * 根据用户名查询用户信息
     *
     * @param name
     * @return
     */
    @Select("select id,name,password,salt,nickname,image,phone,status,email,login_time loginTime,created_time createdTime from wm_user where name = #{name}")
    WmUser findUserByName(String name);

    /**
     * 通过自媒体用户信息
     * @param id
     * @return
     */
    @Select("select id,ap_user_id apUserId,ap_author_id apAuthorId,name,password,salt,nickname,image,location,phone,status,email,type,score,login_time loginTime,created_time createdTime from wm_user where id = #{id}")
    WmUser findById(@Param("id") Integer id);
}
