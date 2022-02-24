package com.heima.search.mapper;

import com.heima.model.search.dtos.UserSearchDto;
import com.heima.model.search.pojos.ApAssociateWords;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author: tang
 * @date: Create in 18:30 2021/9/16
 * @description: 联想关系表
 */
public interface ApAssociateWordsMapper {

    /**
     * 联想消息
     * @param searchWords
     * @return
     */
    @Select("select * from ap_associate_words where associate_words like '%${searchWords}%' limit #{pageSize}")
    List<ApAssociateWords> search(@Param("searchWords") String searchWords,@Param("pageSize") Integer pageSize);

    /**
     *
     * @return
     */
    @Select("select * from ap_associate_words")
    List<ApAssociateWords> findAll();
}
