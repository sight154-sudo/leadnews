package com.heima.article.mapper;

import com.heima.model.article.dtos.ArticleHomeDto;
import com.heima.model.article.pojos.ApArticle;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author: tang
 * @date: Create in 19:54 2021/9/8
 * @description:
 */
public interface ApArticleMapper {

    /**
     * 保存用户详情信息
     * @param aparticle
     * @return
     */
    int saveArticle(ApArticle aparticle);

    /**
     * 根据条件分页查询发布的文章信息
     * @param type
     * @param dto
     */
    List<ApArticle> findList(@Param("type") Short type, @Param("dto") ArticleHomeDto dto);

    /**
     * 根据id查询文章详情信息
     * @param id
     * @return
     */
    @Select("select * from ap_article where id = #{id}")
    ApArticle findById(Long id);

    /**
     * 查询所有
     * @return
     */
    @Select("select * from ap_article")
    List<ApArticle> findAll();
}
