package com.heima.behavior.mapper;

import com.heima.model.behavior.pojos.ApBehaviorEntry;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author: tang
 * @date: Create in 21:28 2021/9/11
 * @description:
 */
public interface ApBehaviorEntryMapper {

    /**
     * 添加用户基本信息行为
     * @param apBehaviorEntry
     */
    @Insert("insert into ap_behavior_entry(id,type,entry_id,created_time) " +
            "values(#{id},#{type},#{entryId},#{createdTime})")
    void insert(ApBehaviorEntry apBehaviorEntry);

    /**
     * 查询
     * @param type
     * @return
     */
    @Select("select * from ap_behavior_entry where type = #{type} and entry_id = #{entryId}")
    ApBehaviorEntry findByTypeAndEntryId(@Param("type") Short type,@Param("entryId") Integer entryId);
}
