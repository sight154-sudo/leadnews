package com.heima.behavior;

import com.heima.model.behavior.dtos.LikesBehaviorDto;
import com.heima.model.behavior.dtos.UnLikesBehaviorDto;
import com.heima.model.common.dtos.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author king
 */
public interface ApUnLikesBehaviorControllerApi {

   /**
     * 保存点赞行为
     * @param dto
     * @return
     */
	ResponseResult unlike(UnLikesBehaviorDto dto);

    /**
     *
     * @param entryId
     * @param articleId
     * @return
     */
    Boolean findUnLikeByArticleIdAndEntryId(@RequestParam("entryId") Integer entryId, @RequestParam("articleId") Long articleId);
}