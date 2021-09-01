package com.heima.user.controller.v1;

import com.heima.common.constants.user.AdminConstans;
import com.heima.model.user.dtos.AuthDto;
import com.heima.user.ApUserRealnameControllerApi;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.user.service.ApUserRealnameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: tang
 * @date: Create in 19:30 2021/8/30
 * @description:
 */
@RestController
@RequestMapping("api/v1/auth")
public class ApUserRealnameController implements ApUserRealnameControllerApi {

    @Autowired
    private ApUserRealnameService apUserRealnameService;

    @PostMapping("list")
    @Override
    public ResponseResult loadListByStatus(AuthDto dto) {

        return apUserRealnameService.loadListByStatus(dto);
    }

    @PostMapping("authFail")
    @Override
    public ResponseResult authFail(@RequestBody AuthDto dto) {
        return apUserRealnameService.updateStatusById(dto, AdminConstans.AUTH_FAIL);
    }

    @PostMapping("authPass")
    @Override
    public ResponseResult authPass(@RequestBody AuthDto dto) {
        return apUserRealnameService.updateStatusById(dto,AdminConstans.AUTH_PASS);
    }
}
