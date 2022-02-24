package com.heima.behavior;

import com.heima.model.behavior.dtos.LikesBehaviorDto;
import com.heima.model.common.dtos.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author king
 */
public interface ApLikesBehaviorControllerApi {


   /**
     * 保存点赞行为
     * @param dto
     * @return
     */
	ResponseResult like(LikesBehaviorDto dto);

    /**
     *
     * @param entryId
     * @param articleId
     * @param type
     * @return
     */
    Boolean findLikeByArticleIdAndEntryId(@RequestParam("entryId") Integer entryId, @RequestParam("articleId") Long articleId, @RequestParam("type") short type);
}