package com.heima.article.mapper;

import com.heima.model.article.pojos.ApAuthor;
import org.apache.ibatis.annotations.*;

/**
 * @author: tang
 * @date: Create in 22:18 2021/8/30
 * @description:
 */
public interface ApAuthorMapper {

    /**
     * 根据id查询文章作者信息
     * @param id
     * @return
     */
    @Select("select id,name,type,user_id userId,created_time createdTime,wm_user_id wmUserId from ap_author where id = #{id}")
    ApAuthor findById(Integer id);

    /**
     * 保存文章作者
     * @param apAuthor
     * @return
     */
    @Insert("insert into ap_author(id,name,type,user_id,created_time,wm_user_id) values(null,#{name},#{type},#{userId},#{createdTime},#{wmUserId})")
    /*@Options(useGeneratedKeys = true,keyProperty = "id")*/
    int save(ApAuthor apAuthor);

    /**
     * 更新作者关联的自媒体人信息
     * @param apAuthor
     * @return
     */
    @Update("update ap_author set wm_user_id = #{wmUserId}")
    int updateWmUserId(ApAuthor apAuthor);

    /**
     * 通过name查询文章作者信息
     * @param name
     * @return
     */
    @Select("select id,name,type,user_id userId,created_time createdTime,wm_user_id wmUserId from ap_author where name = #{name}")
    ApAuthor findByName(String name);
}
