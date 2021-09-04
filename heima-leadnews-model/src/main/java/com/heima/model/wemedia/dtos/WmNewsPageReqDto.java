package com.heima.model.wemedia.dtos;

import com.heima.model.common.dtos.PageRequestDto;
import lombok.Data;

/**
 * @author: tang
 * @date: Create in 19:24 2021/9/4
 * @description:
 */
@Data
public class WmNewsPageReqDto extends PageRequestDto {
    /**
     * 关键字
     */
    private String keyword;
    /**
     * 频道Id
     */
    private Integer channelId;
    /**
     * 起始时间
     */
    private String beginPubDate;
    /**
     * 截止时间
     */
    private String endPubDate;
    /**
     * 状态
     */
    private Short status;

    /**
     * 用户id
     */
    private Integer userId;
}
