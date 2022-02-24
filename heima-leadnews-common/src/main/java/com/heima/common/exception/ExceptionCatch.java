package com.heima.common.exception;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: tang
 * @date: Create in 11:28 2021/8/29
 * @description: 全局异常处理器
 */
@ControllerAdvice
@Slf4j
@Component
public class ExceptionCatch {

    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public ResponseResult doBusinessException(BusinessException e){
        log.error("catch exception{}",e);
        return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR);
    }

    @ExceptionHandler({SystemException.class})
    @ResponseBody
    public ResponseResult doSystemException(SystemException e){
        log.error("catch exception{}",e);
        return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult doException(Exception e){
        log.error("catch exception{}",e);
        return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR);
    }
}
