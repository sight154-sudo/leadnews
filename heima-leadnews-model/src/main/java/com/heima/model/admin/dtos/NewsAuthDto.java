package com.heima.model.admin.dtos;

import com.heima.model.common.dtos.PageRequestDto;
import lombok.Data;

/**
 * @author: tang
 * @date: Create in 19:23 2021/9/9
 * @description:
 */
@Data
public class NewsAuthDto extends PageRequestDto {
    /**
     * 标题
     */
    private String title;
    /**
     * 文章id
     */
    private Integer id;
    /**
     * 驳回原因
     */
    private String msg;
    /**
     * 文章状态
     */
    private Short status;
}
