package com.heima.wemedia.controller.v1;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.WmMaterialDto;
import com.heima.wemedia.MaterialManageControllerApi;
import com.heima.wemedia.service.MaterialManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: tang
 * @date: Create in 20:54 2021/9/3
 * @description:
 */
@RestController
@RequestMapping("api/v1/material")
public class MaterialManageController implements MaterialManageControllerApi {

    @Autowired
    private MaterialManageService materialManageService;

    @PostMapping("list")
    @Override
    public ResponseResult findAll(@RequestBody WmMaterialDto dto) {
        //wemedia/api/v1/material/list
        return materialManageService.findAll(dto);
    }
    @GetMapping("del_picture/{id}")
    @Override
    public ResponseResult delPic(@PathVariable Integer id) {
        return materialManageService.delPic(id);
    }

    @GetMapping("cancel_collect/{id}")
    @Override
    public ResponseResult cancleCollectionMaterial(@PathVariable Integer id) {
        return materialManageService.updateIsCollection(id,false);
    }

    @GetMapping("collect/{id}")
    @Override
    public ResponseResult collectionMaterial(@PathVariable Integer id) {
        return materialManageService.updateIsCollection(id,true);
    }
}
