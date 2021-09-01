package com.heima.model.admin.pojos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: tang
 * @date: Create in 15:27 2021/8/29
 * @description: 敏感词实体类
 */
@Data
public class AdSensitive implements Serializable {
    private Integer id;
    private String sensitives;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createdTime;
}
