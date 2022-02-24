package com.heima.article.mapper;

import com.heima.model.article.pojos.ApArticleConfig;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author: tang
 * @date: Create in 20:24 2021/9/8
 * @description:
 */
public interface ApArticleConfigMapper {

    /**
     * 保存文章配置信息
     * @param config
     * @return
     */
    @Insert("insert into ap_article_config values(#{id},#{articleId},#{isComment},#{isForward},#{isDown},#{isDelete})")
    int saveAparticelConfig(ApArticleConfig config);

    /**
     * 查询文章配置信息
     * @param articleId
     * @return
     */
    @Select("select * from ap_article_config where article_id = #{articleId}")
    ApArticleConfig findByArticleId(Long articleId);

    /**
     * 修改文章信息信息
     * @param enable
     * @param articleId
     */
    @Select("update ap_article_config set enable = #{enable} where article_id = #{articleId}")
    void updateEnable(@Param("enable") Boolean enable, @Param("articleId") Long articleId);
}
