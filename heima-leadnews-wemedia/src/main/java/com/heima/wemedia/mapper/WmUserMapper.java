package com.heima.wemedia.mapper;

import com.heima.model.wemedia.pojos.WmUser;
import org.apache.ibatis.annotations.Select;

/**
 * @author: tang
 * @date: Create in 21:39 2021/8/30
 * @description:
 */
public interface WmUserMapper {

    /**
     * 保存自媒体用户
     * @param wmUser
     * @return
     */
    int save(WmUser wmUser);

    /**
     * 根据自媒体用户名查询
     * @param name
     * @return
     */
    @Select("select id,ap_user_id apUserId,ap_author_id apAuthorId,name,password,salt,nickname,image,location,phone,status,email,type,score,login_time loginTime,created_time createdTime from wm_user where name = #{name}")
    WmUser findByName(String name);
}
