package com.heima.behavior.controller.v1;

import com.heima.behavior.ApUnLikesBehaviorControllerApi;
import com.heima.behavior.service.ApUnLikesBehaviorService;
import com.heima.model.behavior.dtos.UnLikesBehaviorDto;
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
public class ApUnLikesBehaviorController implements ApUnLikesBehaviorControllerApi {


    @Autowired
    private ApUnLikesBehaviorService apUnLikesBehaviorService;

    @PostMapping("unlike_behavior")
    @Override
    public ResponseResult unlike(@RequestBody UnLikesBehaviorDto dto) {
        return apUnLikesBehaviorService.unlike(dto);
    }
    @GetMapping("un_likes_behavior/one")
    @Override
    public Boolean findUnLikeByArticleIdAndEntryId(@Param("entryId") Integer entryId,@Param("articleId") Long articleId) {
        return apUnLikesBehaviorService.findExist(entryId,articleId);
    }
}
