package com.heima.article.mapper;

import com.heima.model.article.pojos.ApArticleConfig;
import org.apache.ibatis.annotations.Insert;

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
}
