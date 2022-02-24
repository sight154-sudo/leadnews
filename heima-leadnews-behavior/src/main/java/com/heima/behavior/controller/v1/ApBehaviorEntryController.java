package com.heima.behavior.controller.v1;

import com.heima.behavior.ApBehaviorEntryControllerApi;
import com.heima.behavior.service.ApBehaviorEntryService;
import com.heima.behavior.service.ApLikesBehaviorService;
import com.heima.model.behavior.dtos.ApBehaviorEntryDto;
import com.heima.model.behavior.pojos.ApBehaviorEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: tang
 * @date: Create in 20:37 2021/9/12
 * @description:
 */
@RestController
@RequestMapping("api/v1")
public class ApBehaviorEntryController implements ApBehaviorEntryControllerApi {

    @Autowired
    private ApBehaviorEntryService apBehaviorEntryService;
    /**
     * 查询用户行为实体
     * @param dto
     * @return
     */
    @PostMapping("findBehaviorEntry")
    @Override
    public ApBehaviorEntry findByTypeAndEntryId(@RequestBody ApBehaviorEntryDto dto) {
        return apBehaviorEntryService.findByTypeAndEntryId(dto.getType(),dto.getEntryId());
    }
}
