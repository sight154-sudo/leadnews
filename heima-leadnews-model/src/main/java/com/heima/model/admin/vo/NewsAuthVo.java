package com.heima.model.admin.vo;

import com.heima.model.wemedia.pojos.WmNews;
import lombok.Data;

/**
 * @author: tang
 * @date: Create in 19:36 2021/9/9
 * @description:
 */
@Data
public class NewsAuthVo extends WmNews {
    /**
     * 作者名称
     */
    private String name;
}
