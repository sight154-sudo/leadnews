package com.heima.user.mapper;

import com.heima.model.user.pojos.ApUserFollow;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author: tang
 * @date: Create in 19:39 2021/9/11
 * @description:
 */
public interface ApUserFollowMapper {
    /**
     * 根据用户id与关注作者id查询数据
     * @param userId
     * @param followId
     * @return
     */
    @Select("select * from ap_user_follow where user_id = #{userId} and follow_id = #{followId}")
    ApUserFollow findByUserIdAndArticleId(@Param("userId") Integer userId,@Param("followId") Integer followId);

    /**
     * 保存关注关系
     * @param apUserFollow
     */
    @Insert("insert into ap_user_follow(id,user_id,follow_id,follow_name,level,is_notice,created_time) " +
            "values(#{id},#{userId},#{followId},#{followName},#{level},#{isNotice},#{createdTime})")
    void save(ApUserFollow apUserFollow);

    /**
     * 解除关注关系
     * @param userId
     * @param followId
     */
    @Delete("delete from ap_user_follow where user_id = #{userId} and follow_id = #{followId}")
    void delete(@Param("userId") Integer userId,@Param("followId") Integer followId);
}
