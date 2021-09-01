package com.heima.model.admin.dtos;

import com.heima.model.common.dtos.PageRequestDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: tang
 * @date: Create in 15:31 2021/8/29
 * @description:
 */
@Data
@ApiModel("敏感词分页查询的条件类")
public class AdSensitiveDto extends PageRequestDto {
    @ApiModelProperty("敏感词名称")
    private String sensitives;
}
