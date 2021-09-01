package com.heima.common.exception;

/**
 * @author: tang
 * @date: Create in 11:32 2021/8/29
 * @description:
 */
public class BusinessException extends RuntimeException{
    public BusinessException(String message) {
        super(message);
    }
}
