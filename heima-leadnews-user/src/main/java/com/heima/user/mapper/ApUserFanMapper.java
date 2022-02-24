package com.heima.user.mapper;

import com.heima.model.user.pojos.ApUserFan;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author: tang
 * @date: Create in 19:42 2021/9/11
 * @description:
 */
public interface ApUserFanMapper {

    /**
     * 保存用户的粉丝信息
     * @param apUserFan
     */
    @Insert("insert into ap_user_fan(id,user_id,fans_id,fans_name,level,created_time,is_display,is_shield_letter,is_shield_comment) " +
            "values(#{id},#{userId},#{fansId},#{fansName},#{level},#{createdTime},#{isDisplay},#{isShieldLetter},#{isShieldComment})")
    void insert(ApUserFan apUserFan);

    /**
     * 查询用户的粉线信息
     * @param userId
     * @param fansId
     * @return
     */
    @Select("select * from ap_user_fan where user_id = #{userId} and fans_id = #{fansId}")
    ApUserFan findByUserIdAndFansId(@Param("userId") Integer userId,@Param("fansId") Integer fansId);


    /**
     * 取消粉丝关系
     * @param userId
     * @param fansId
     */
    @Delete("delete from ap_user_fan where user_id = #{userId} and fans_id = #{fansId}")
    void delete(@Param("userId") Integer userId,@Param("fansId") Integer fansId);
}
