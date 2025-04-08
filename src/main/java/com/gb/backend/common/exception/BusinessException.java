package com.gb.backend.common.exception;

import com.gb.backend.common.constant.ResultCode;
import lombok.Getter;
/**
 * 业务异常类
 * 用于处理业务逻辑异常，区别于系统异常
 * @since 2024-04-08
 */
@Getter
public class BusinessException extends RuntimeException {
    /**
     * 错误码
     */
    private final Integer code;

    /**
     * 构造函数
     *
     * @param message 错误信息
     */
    public BusinessException(String message) {
        super(message);
        this.code = ResultCode.BUSINESS_ERROR;
    }

    /**
     * 构造函数
     *
     * @param code    错误码
     * @param message 错误信息
     */
    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
    }
} 