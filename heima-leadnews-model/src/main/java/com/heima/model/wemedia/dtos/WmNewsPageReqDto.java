package com.heima.model.wemedia.dtos;

import com.heima.model.common.dtos.PageRequestDto;
import lombok.Data;

import java.util.Date;

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
    private Date beginPubDate;
    /**
     * 截止时间
     */
    private Date endPubDate;
    /**
     * 状态
     */
    private Short status;

    /**
     * 用户id
     */
    private Integer userId;
}
