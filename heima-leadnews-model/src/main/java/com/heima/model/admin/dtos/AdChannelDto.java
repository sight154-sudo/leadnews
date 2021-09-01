package com.heima.model.admin.dtos;

import com.heima.model.common.dtos.PageRequestDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: tang
 * @date: Create in 20:10 2021/8/28
 * @description:
 */
@Data
@ApiModel("频道分页查询的条件类")
public class AdChannelDto extends PageRequestDto {

    @ApiModelProperty("频道名称")
    private String name;
}
