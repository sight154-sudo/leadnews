package com.heima.admin.controller.v1;

import com.heima.admin.AdSensitiveControllerApi;
import com.heima.admin.service.AdSensitiveService;
import com.heima.model.admin.dtos.AdSensitiveDto;
import com.heima.model.admin.pojos.AdSensitive;
import com.heima.model.common.dtos.PageResponseResult;
import com.heima.model.common.dtos.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: tang
 * @date: Create in 15:38 2021/8/29
 * @description:
 */
@RestController
@RequestMapping("api/v1/sensitive")
public class AdSensitiveController implements AdSensitiveControllerApi {

    @Autowired
    private AdSensitiveService adSensitiveService;

    @PostMapping("list")
    @Override
    public ResponseResult<PageResponseResult> findAllByName(@RequestBody AdSensitiveDto dto) {
        return adSensitiveService.findAllByName(dto);
    }

    @PostMapping("save")
    @Override
    public ResponseResult saveSensitive(@RequestBody AdSensitive adSensitive) {
        return adSensitiveService.save(adSensitive);
    }

    @PostMapping("update")
    @Override
    public ResponseResult updateSensitive(@RequestBody AdSensitive adSensitive) {
        return adSensitiveService.update(adSensitive);
    }

    @PostMapping("del/{id}")
    @Override
    public ResponseResult deleteSensitiveById(@PathVariable Integer id) {
        return adSensitiveService.delete(id);
    }
}
