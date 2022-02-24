package com.heima.behavior.controller.v1;

import com.heima.behavior.ApLikesBehaviorControllerApi;
import com.heima.behavior.service.ApLikesBehaviorService;
import com.heima.model.behavior.dtos.LikesBehaviorDto;
import com.heima.model.common.dtos.ResponseResult;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: tang
 * @date: Create in 11:06 2021/9/12
 * @description:
 */
@RestController
@RequestMapping("api/v1")
public class ApLikesBehaviorController implements ApLikesBehaviorControllerApi {

    @Autowired
    private ApLikesBehaviorService apLikesBehaviorService;

    @PostMapping("likes_behavior")
    @Override
    public ResponseResult like(@RequestBody LikesBehaviorDto dto) {
        return apLikesBehaviorService.like(dto);
    }

    @GetMapping("likes_behavior/one")
    @Override
    public Boolean findLikeByArticleIdAndEntryId(@RequestParam("entryId") Integer entryId, @RequestParam("articleId") Long articleId, @RequestParam("type") short type) {
        return apLikesBehaviorService.findExists(entryId,articleId,type);
    }
}
