package com.heima.user.mapper;

import com.heima.model.user.pojos.ApUserRealname;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author: tang
 * @date: Create in 19:35 2021/8/30
 * @description:
 */
public interface ApUserRealnameMapper {

    /**
     * 根据条件分页查询用户审核信息
     * @param status
     * @return
     */
    List<ApUserRealname> findAll(@Param("status") Short status);

    /**
     * 更改用户状态信息
     * @param apUserRealname
     * @return
     */
    @Update("<script>update ap_user_realname \n" +
            "        <set>\n" +
            "            <if test=\"status != null\">\n" +
            "                status = #{status},\n" +
            "            </if>\n" +
            "            <if test=\"reason != null and reason != ''\">\n" +
            "                reason = #{msg}\n" +
            "            </if>\n" +
            "        </set>\n" +
            "        <where>\n" +
            "            id = #{id}\n" +
            "        </where></script>")
    int updateStatusById(ApUserRealname apUserRealname);

    ApUserRealname findById(@Param("id") Integer id);
}
