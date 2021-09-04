package com.heima.admin.controller.v1;

import com.heima.admin.service.AdChannelService;
import com.heima.admin.AdChannelControllerApi;
import com.heima.model.admin.dtos.AdChannelDto;
import com.heima.model.admin.pojos.AdChannel;
import com.heima.model.common.dtos.PageResponseResult;
import com.heima.model.common.dtos.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: tang
 * @date: Create in 20:11 2021/8/28
 * @description:
 */
@RestController
@RequestMapping("/api/v1/channel")
public class AdChannelController implements AdChannelControllerApi {

    @Autowired
    private AdChannelService adChannelService;

    @PostMapping("list")
    @Override
    public ResponseResult<PageResponseResult> findAllByName(@RequestBody AdChannelDto dto) {
        return adChannelService.findAllByName(dto);
    }

    @PostMapping("save")
    @Override
    public ResponseResult saveChannel(@RequestBody AdChannel adChannel) {
        return adChannelService.save(adChannel);
    }

    @PostMapping("update")
    @Override
    public ResponseResult updateChannel(@RequestBody AdChannel adChannel) {
        return adChannelService.update(adChannel);
    }

    @GetMapping("del/{id}")
    @Override
    public ResponseResult deleteChannel(@PathVariable Integer id) {
        return adChannelService.delete(id);
    }

    @GetMapping("channels")
    @Override
    public ResponseResult findAll() {
        return adChannelService.findAll();
    }
}
