package com.heima.model.user.pojos;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: tang
 * @date: Create in 15:08 2021/8/30
 * @description:APP实名认证信息表
 */
@Data
public class ApUserRealname implements Serializable {

    private Integer id;
    private Integer userId;//账号id
    private String name;//用户名称
    private String idno;//资源名称
    private String fontImage;//正面照片
    private String backImage;//背面照片
    private String holdImage;//手持照片
    private String liveImage;//活体照片
    private Short status;//状态  0 创建中  1 待审查   2 审核失败   9  审核通过
    private String reason;//拒绝原因
    private Date createdTime;//创建时间
    private Date submitedTime;//提交时间
    private Date updatedTime;//更新时间
}
