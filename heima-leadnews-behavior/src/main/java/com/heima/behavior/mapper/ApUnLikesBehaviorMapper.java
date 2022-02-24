package com.heima.behavior.mapper;

import com.heima.model.behavior.pojos.ApReadBehavior;
import com.heima.model.behavior.pojos.ApUnlikesBehavior;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author: tang
 * @date: Create in 18:28 2021/9/12
 * @description:
 */
public interface ApUnLikesBehaviorMapper {

    /**
     * 查询用户阅读行为
     * @param entryId
     * @param articleId
     * @return
     */
    @Select("select * from ap_unlikes_behavior where entry_id = #{entryId} and article_id = #{articleId}")
    ApUnlikesBehavior findByEntryIdAndArticleId(@Param("entryId") Integer entryId,@Param("articleId") Long articleId);
    /**
     * 保存用户阅读数据
     * @param apUnlikesBehavior
     */
    @Insert("insert into ap_unlikes_behavior values(#{id},#{entryId},#{articleId},#{type},#{createdTime})")
    void insert(ApUnlikesBehavior apUnlikesBehavior);

    /**
     * 修改用户阅读数据
     * @param apUnlikesBehavior
     */
    @Update("<script> update ap_unlikes_behavior\n" +
            "        <set>\n" +
            "            <if test=\"entryId != null\">\n" +
            "                entry_id = #{entryId},\n" +
            "            </if>\n" +
            "            <if test=\"articleId != null\">\n" +
            "                article_id = #{articleId},\n" +
            "            </if>\n" +
            "            <if test=\"type != null\">\n" +
            "                type = #{type},\n" +
            "            </if>\n" +
            "            <if test=\"createdTime != null\">\n" +
            "                created_time = #{createdTime}\n" +
            "            </if>\n" +
            "        </set>\n" +
            "        where id = #{id}</script>")
    void update(ApUnlikesBehavior apUnlikesBehavior);
}
