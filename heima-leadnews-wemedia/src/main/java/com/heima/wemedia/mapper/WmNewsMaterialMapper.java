package com.heima.wemedia.mapper;

import com.heima.model.wemedia.pojos.WmNewsMaterial;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

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

    /**
     * 批量保存文章引用素材的关系
     * @param newsId
     * @param materialIds
     * @param type
     * @return
     */
    int saveBatch(@Param("newsId") Integer newsId, @Param("materialIds") List<Object> materialIds, @Param("type") Short type );

    /**
     * 批量删除文章引用素材的关系
     * @param newsId
     * @param type
     * @return
     */
    int deleteBatch(@Param("newsId") Integer newsId,@Param("type")  short type);

    /**
     * 根据id删除
     * @param newsId
     */
    @Delete("delete from wm_news_material where news_id = #{newsId}")
    void deleteById(Integer newsId);
}
