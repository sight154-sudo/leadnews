package com.heima.search.mapper;

import com.heima.model.search.pojos.ApUserSearch;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author: tang
 * @date: Create in 21:06 2021/9/14
 * @description:
 */
public interface ApUserSearchMapper {

    /**
     * 查询用户搜索历史记录
     * @return
     */
    @Select("select * from ap_user_search where entry_id = #{entryId} and status = #{status} limit #{size}")
    List<ApUserSearch> findAll(@Param("entryId") Integer entryId,@Param("status") Integer status,@Param("size") Integer size);

    /**
     * 修改状态
     * @param entryId
     * @param status
     */
    @Update("update ap_user_search set status = #{status} where entry_id = #{entryId}")
    void update(@Param("entryId") Integer entryId,@Param("status") Integer status);

    /**
     * 查询用户的搜索记录
     * @param entryId
     * @param keywords
     * @return
     */
    @Select("select * from ap_user_search where entry_id = #{entryId} and keyword = #{keyword}")
    ApUserSearch findByEntryAndKeyWord(@Param("entryId") Integer entryId,@Param("keyword") String keywords);

    /**
     * 添加数据
     * @param apUserSearch
     */
    @Insert("insert into ap_user_search values(#{id},#{entryId},#{keyword},#{status},#{createdTime})")
    void insert(ApUserSearch apUserSearch);
}
