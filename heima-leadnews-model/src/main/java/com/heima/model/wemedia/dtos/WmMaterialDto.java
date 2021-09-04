package com.heima.model.wemedia.dtos;

import com.heima.model.common.dtos.PageRequestDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: tang
 * @date: Create in 20:46 2021/9/3
 * @description:
 */
@Data
@ApiModel("素材管理")
public class WmMaterialDto extends PageRequestDto {
    private Integer userId;
    @ApiModelProperty("素材管理中图片是否收藏")
    private Integer isCollection;
}
