package com.heima.admin.service;

import com.heima.model.admin.dtos.AdSensitiveDto;
import com.heima.model.admin.pojos.AdSensitive;
import com.heima.model.common.dtos.ResponseResult;

/**
 * @author: tang
 * @date: Create in 15:37 2021/8/29
 * @description:
 */
public interface AdSensitiveService {
    /**
     * 根据敏感词名称查询
     * @param dto
     * @return
     */
    ResponseResult findAllByName(AdSensitiveDto dto);

    /**
     * 新增敏感词信息
     * @param sensitive
     * @return
     */
    ResponseResult save(AdSensitive sensitive);

    /**
     * 修改敏感词信息
     * @param sensitive
     * @return
     */
    ResponseResult update(AdSensitive sensitive);

    /**
     * 删除敏感词信息
     * @param id
     * @return
     */
    ResponseResult delete(Integer id);
}
