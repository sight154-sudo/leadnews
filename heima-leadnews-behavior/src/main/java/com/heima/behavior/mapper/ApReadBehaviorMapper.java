package com.heima.behavior.mapper;

import com.heima.model.behavior.pojos.ApReadBehavior;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author: tang
 * @date: Create in 18:28 2021/9/12
 * @description:
 */
public interface ApReadBehaviorMapper {

    /**
     * 查询用户阅读行为
     * @param entryId
     * @param articleId
     * @return
     */
    @Select("select * from ap_read_behavior where entry_id = #{entryId} and article_id = #{articleId}")
    ApReadBehavior findByEntryIdAndArticleId(@Param("entryId") Integer entryId,@Param("articleId") Long articleId);

    /**
     * 保存用户阅读数据
     * @param apReadBehavior
     */
    @Insert("insert into ap_read_behavior values(#{id},#{entryId},#{articleId},#{count},#{readDuration},#{percentage},#{loadDuration},#{createdTime},#{updatedTime})")
    void insert(ApReadBehavior apReadBehavior);

    /**
     * 修改用户阅读数据
     * @param apReadBehavior
     */
    void update(ApReadBehavior apReadBehavior);
}
