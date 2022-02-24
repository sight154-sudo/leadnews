package com.heima.article.mapper;

import com.heima.model.behavior.dtos.CollectionBehaviorDto;
import com.heima.model.behavior.pojos.ApCollection;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author: tang
 * @date: Create in 20:49 2021/9/12
 * @description:
 */
public interface ApCollectionMapper {

    /**
     * 查询文章或动态是否存在
     * @param entryId
     * @param articleId
     * @return
     */
    @Select("select * from ap_collection where entry_id = #{entryId} and article_id = #{articleId}")
    ApCollection findByEntryIdAndArticleId(@Param("entryId") Integer entryId,@Param("articleId") Long articleId);

    /**
     * 添加收藏
     * @param apCollection
     */
    @Insert("insert into ap_collection values(#{id},#{entryId},#{articleId},#{type},#{collectionTime},#{publishedTime})")
    void insert(ApCollection apCollection);

    /**
     * 取消收藏
     * @param apCollection
     */
    @Delete("delete from ap_collection where id = #{id}")
    void delete(ApCollection apCollection);
}
