package com.heima.search.feign;

import com.heima.model.behavior.dtos.ApBehaviorEntryDto;
import com.heima.model.behavior.pojos.ApBehaviorEntry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author: tang
 * @date: Create in 21:00 2021/9/14
 * @description:
 */
@FeignClient("leadnews-behavior")
public interface BehaviorFeign {
    /**
     * 查询用户行为实体
     * @param dto
     * @return
     */
    @PostMapping("api/v1/findBehaviorEntry")
    ApBehaviorEntry findByTypeAndEntryId(@RequestBody ApBehaviorEntryDto dto);
}
