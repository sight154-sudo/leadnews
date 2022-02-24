package com.heima.article.mapper;

import com.heima.model.article.pojos.ApArticleContent;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

/**
 * @author: tang
 * @date: Create in 20:25 2021/9/8
 * @description:
 */
public interface ApArticleContentMapper {
    /**
     * 保存文章内容信息
     * @param apArticleContent
     * @return
     */
    @Insert("insert into ap_article_content values(#{id},#{articleId},#{content})")
    int saveApArticleContent(ApArticleContent apArticleContent);

    @Select("select * from ap_article_content where article_id = #{articleId}")
    ApArticleContent findByArticleId(Long articleId);
}
