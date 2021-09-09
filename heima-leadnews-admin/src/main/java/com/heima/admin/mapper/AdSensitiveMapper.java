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

    /**
     * 保存敏感词信息
     * @param adSensitive
     * @return
     */
    @Insert("insert into ad_sensitive(id,sensitives,created_time) values(#{id},#{sensitives},#{createdTime})")
    int save(AdSensitive adSensitive);

    /**
     * 修改敏感词信息
     * @param adSensitive
     * @return
     */
    int update(AdSensitive adSensitive);

    /**
     * 通过id删除敏感词信息
     * @param id
     * @return
     */
    @Delete("delete from ad_sensitive where id = #{id}")
    int delete(Integer id);

    /**
     * 通过Id查询敏感词信息
     * @param id
     * @return
     */
    @Select("select id,sensitives,created_time createdTime from ad_sensitive where id = #{id}")
    AdSensitive findById(Integer id);

    /**
     * 查询所有敏感词
     * @return
     */
    @Select("select sensitives from ad_sensitive")
    List<String> findSensitive();
}
