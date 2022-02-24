package com.heima.article.feign;

import com.heima.model.behavior.dtos.ApBehaviorEntryDto;
import com.heima.model.behavior.pojos.ApBehaviorEntry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author: tang
 * @date: Create in 20:31 2021/9/12
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

    /**
     * 查询是否不喜欢
     * @param entryId
     * @param articleId
     * @return
     */
    @GetMapping("/api/v1/un_likes_behavior/one")
    Boolean findUnLikeByArticleIdAndEntryId(@RequestParam("entryId") Integer entryId, @RequestParam("articleId") Long articleId);

    /**
     * 查询是否点赞
     * @param entryId
     * @param articleId
     * @param type
     * @return
     */
    @GetMapping("/api/v1/likes_behavior/one")
    Boolean findLikeByArticleIdAndEntryId(@RequestParam("entryId") Integer entryId, @RequestParam("articleId") Long articleId, @RequestParam("type") Short type);
}
