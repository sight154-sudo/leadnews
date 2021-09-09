package com.heima.wemedia.mapper;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.WmNewsDto;
import com.heima.model.wemedia.dtos.WmNewsPageReqDto;
import com.heima.model.wemedia.pojos.WmNews;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author: tang
 * @date: Create in 10:22 2021/9/4
 * @description:
 */
public interface WmNewsMapper {

    /**
     * 根据条件查询所有内容列表
     * @param dto
     * @return
     */
    List<WmNews> findAll(WmNewsPageReqDto dto);

    /**
     * 保存文章信息
     * @param wmNews
     * @return
     */
    int save(WmNews wmNews);

    /**
     * 修改文章信息
     * @param wmNews
     * @return
     */
    int update(WmNews wmNews);

    /**
     * 根据id查询
     * @param newsId
     * @return
     */
    @Select("select id,user_id userId,title,content,type,channel_id channelId,labels,created_time createdTime," +
            "submited_time submitedTime,status,publish_time publishTime,reason,article_id articleId,images,enable from wm_news where id = #{newsId}")
    WmNews findById(Integer newsId);

    /**
     * 根据id删除
     * @param id
     */
    @Delete("delete from wm_news where id = #{id}")
    void deleteById(Integer id);

    /**
     * 修改文章的上下架状态
     * @param id
     * @param enable
     */
    @Update("update wm_news set enable = #{enable} where id = #{id}")
    void updateEnableById(Integer id, Boolean enable);

    /**
     * 修改文章状态信息
     * @param wmNews
     * @return
     */
    @Update("<script>update wm_news \n" +
            "        <set>\n" +
            "            <if test=\"status != null\">\n" +
            "                status = #{status},\n" +
            "            </if>\n" +
            "            <if test=\"reason != null and reason != ''\">\n" +
            "                reason = #{reason}\n" +
            "            </if>\n" +
            "        </set>\n" +
            "        where id = #{id}</script>")
    int updateWmNewsStatus(WmNews wmNews);
}
