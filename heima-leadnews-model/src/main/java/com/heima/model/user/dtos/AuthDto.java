package com.heima.model.user.dtos;

import com.heima.model.common.dtos.PageRequestDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: tang
 * @date: Create in 19:22 2021/8/30
 * @description:
 */
@Data
@ApiModel("用户实名认证分页条件")
public class AuthDto extends PageRequestDto {

    @ApiModelProperty("主键id")
    private Integer id;
    @ApiModelProperty("驳回信息")
    private String msg;
    @ApiModelProperty("用户状态")
    private Short status;
}
