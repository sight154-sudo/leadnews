package com.heima.behavior.mapper;

import com.heima.model.behavior.pojos.ApFollowBehavior;
import org.apache.ibatis.annotations.Insert;

/**
 * @author: tang
 * @date: Create in 21:59 2021/9/11
 * @description:
 */
public interface ApFollowBehaviorMapper {

    /**
     * 保存用户关注行为信息
     * @param apFollowBehavior
     */
    @Insert("insert into ap_follow_behavior(id,entry_id,article_id,follow_id,created_time) " +
            "values(#{id},#{entryId},#{articleId},#{followId},#{createdTime})")
    void insert(ApFollowBehavior apFollowBehavior);
}
