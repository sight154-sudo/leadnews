package com.heima.admin.mapper;

import com.heima.model.admin.pojos.AdSensitive;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author: tang
 * @date: Create in 15:33 2021/8/29
 * @description:
 */
public interface AdSensitiveMapper {
    /**
     * 根据条件查询所有频道
     * @param name
     * @return
     */
    List<AdSensitive> findAll(@Param("name") String name);

    @Insert("insert into ad_sensitive(id,sensitives,created_time) values(#{id},#{sensitives},#{createdTime})")
    int save(AdSensitive adSensitive);

    int update(AdSensitive adSensitive);

    @Delete("delete from ad_sensitive where id = #{id}")
    int delete(Integer id);

    @Select("select id,sensitives,created_time createdTime from ad_sensitive where id = #{id}")
    AdSensitive findById(Integer id);
}
