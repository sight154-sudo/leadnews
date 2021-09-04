package com.heima.wemedia.service;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.WmMaterialDto;

/**
 * @author: tang
 * @date: Create in 20:55 2021/9/3
 * @description:
 */
public interface MaterialManageService {

    /**
     * 查询所有素材
     * @param dto
     * @return
     */
    public ResponseResult findAll(WmMaterialDto dto);

    /**
     * 根据图片id删除图片
     * @param id
     * @return
     */
    public ResponseResult delPic(Integer id);

    /**
     * 修改收藏状态
     * @param id
     * @param isCollection
     * @return
     */
    public ResponseResult updateIsCollection(Integer id,Boolean isCollection);
}
