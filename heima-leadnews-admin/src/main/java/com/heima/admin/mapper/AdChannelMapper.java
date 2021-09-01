package com.heima.admin.mapper;

import com.github.pagehelper.PageInfo;
import com.heima.model.admin.pojos.AdChannel;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author: tang
 * @date: Create in 20:00 2021/8/28
 * @description:
 */
public interface AdChannelMapper {

    /**
     * 根据条件查询所有频道
     * @param name
     * @return
     */
    List<AdChannel> findAll(@Param("name") String name);

    /**
     * 新增频道信息
     * @param adChannel
     * @return
     */
    int save(AdChannel adChannel);

    /**
     * 修改频道信息
     * @param adchannel
     * @return
     */
    int update(AdChannel adchannel);

    /**
     * 根据id删除频道信息
     * @param id
     * @return
     */
    @Delete("delete from ad_channel where id = #{id}")
    int delete(Integer id);

    /**
     * 根据id查询频道信息
     * @param id
     */
    @Select("select id,name,description,is_default isDefault,status status,ord,created_time createTime from ad_channel where id = #{id}")
    AdChannel findById(Integer id);
}
