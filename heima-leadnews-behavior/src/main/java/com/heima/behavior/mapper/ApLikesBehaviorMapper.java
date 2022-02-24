package com.heima.behavior.mapper;

import com.heima.model.behavior.pojos.ApLikesBehavior;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author: tang
 * @date: Create in 11:10 2021/9/12
 * @description:
 */
public interface ApLikesBehaviorMapper {

    /**
     * 查询点赞行为
     * @param entryId
     * @param articleId
     * @param type
     */
    @Select("select * from ap_likes_behavior where entry_id = #{entryId} and article_id = #{articleId} and type = #{type}")
    ApLikesBehavior findByLikeBehavior(@Param("entryId") Integer entryId,@Param("articleId") Long articleId,@Param("type") Short type);

    /**
     * 保存点赞行为
     * @param apLikesBehavior
     */
    @Insert("insert into ap_likes_behavior values(#{id},#{entryId},#{articleId},#{type},#{operation},#{createdTime})")
    void insert(ApLikesBehavior apLikesBehavior);

    /**
     * 修改点赞行为
     * @param apLikesBehavior
     */
    void update(ApLikesBehavior apLikesBehavior);
}
