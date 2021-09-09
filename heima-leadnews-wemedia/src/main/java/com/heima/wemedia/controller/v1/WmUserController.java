package com.heima.wemedia.controller.v1;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.pojos.WmUser;
import com.heima.wemedia.WmUserControllerApi;
import com.heima.wemedia.service.WmUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: tang
 * @date: Create in 21:39 2021/8/30
 * @description:
 */
@RestController
@RequestMapping("/api/v1/user")
public class WmUserController implements WmUserControllerApi {

    @Autowired
    private WmUserService wmUserService;

    @PostMapping("save")
    @Override
    public ResponseResult save(@RequestBody WmUser wmUser) {
        return wmUserService.save(wmUser);
    }

    @GetMapping("/findByName/{name}")
    @Override
    public WmUser findByName(@PathVariable String name) {

        return wmUserService.findByName(name);
    }
    @GetMapping("findOne/{id}")
    @Override
    public WmUser findWmUserById(@PathVariable Integer id) {
        return wmUserService.findById(id);
    }
}
