package com.heima.wemedia.mapper;

import com.heima.model.wemedia.pojos.WmNewsMaterial;
import org.apache.ibatis.annotations.Select;

/**
 * @author: tang
 * @date: Create in 22:05 2021/9/3
 * @description: 素材引用mapper类
 */
public interface WmNewsMaterialMapper {

    /**
     * 通过id查询图片是否引用
     * @param id
     * @return
     */
    @Select("select id,material_id materialId,news_id newsId,type,ord from wm_news_material where material_id = #{id}")
    WmNewsMaterial findById(Integer id);
}
