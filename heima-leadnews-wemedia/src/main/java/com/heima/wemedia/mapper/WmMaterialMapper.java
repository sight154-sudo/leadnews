package com.heima.wemedia.mapper;

import com.heima.model.wemedia.dtos.WmMaterialDto;
import com.heima.model.wemedia.pojos.WmMaterial;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author: tang
 * @date: Create in 19:45 2021/9/3
 * @description:
 */
public interface WmMaterialMapper {

    /**
     * 新增自媒体图文素材
     * @param wmMaterial
     * @return
     */
    @Insert("insert into wm_material(id,user_id,url,type,is_collection,created_time) " +
            "values(#{id},#{userId},#{url},#{type},#{isCollection},#{createdTime})")
    int save(WmMaterial wmMaterial);

    /**
     * 查询所有素材
     * @param dto
     * @return
     */
    List<WmMaterial> findAll(WmMaterialDto dto);

    /**
     * 根据素材id删除图片
     * @param id
     * @return
     */
    @Delete("delete from wm_material where id = #{id}")
    int delPic(Integer id);

    /**
     * 修改
     * @param wmMaterial
     * @return
     */
    @Update("<script>" +
            "update wm_material\n" +
            "        <set>\n" +
            "            <if test=\"userId != null\">\n" +
            "                user_id = #{userId},\n" +
            "            </if>\n" +
            "            <if test=\"url != null and url != ''\">\n" +
            "                url = #{url}\n" +
            "            </if>\n" +
            "            <if test=\"type != null\">\n" +
            "                type = #{type}\n" +
            "            </if>\n" +
            "            <if test=\"isCollection != null\">\n" +
            "                is_collection = #{isCollection}\n" +
            "            </if>\n" +
            "            <if test=\"createdTime != null\">\n" +
            "                created_time = #{createdTime}\n" +
            "            </if>\n" +
            "        </set>\n" +
            "        <where>\n" +
            "            id = #{id}\n" +
            "        </where></script>")
    int updateIsCollection(WmMaterial wmMaterial);


    @Select("select id,user_id userId,url,type,is_collection isCollection,created_time createdTime" +
            " from wm_material where id = #{id}")
    WmMaterial findById(Integer id);

    /**
     * 根据素材url查询所有
     * @param userId
     * @param url
     * @return
     */
    List<WmMaterial> findAllByUrl(@Param("userId") Integer userId,@Param("urls") List<String> url);
}
