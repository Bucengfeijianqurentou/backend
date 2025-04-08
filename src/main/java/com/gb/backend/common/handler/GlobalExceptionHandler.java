package com.gb.backend.common.handler;

import com.gb.backend.common.Result;
import com.gb.backend.common.constant.ErrorMessage;
import com.gb.backend.common.constant.ResultCode;
import com.gb.backend.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 * 统一处理系统抛出的各类异常
 * @since 2024-04-08
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理业务异常
     *
     * @param e 业务异常
     * @return 统一响应结果
     */
    @ExceptionHandler(BusinessException.class)
    public Result<Void> handleBusinessException(BusinessException e) {
        log.error("业务异常：{}", e.getMessage());
        return Result.error(e.getCode(), e.getMessage());
    }

    /**
     * 处理参数校验异常
     *
     * @param e 参数校验异常
     * @return 统一响应结果
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Void> handleValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        String message = bindingResult.getAllErrors().get(0).getDefaultMessage();
        log.error("参数校验异常：{}", message);
        return Result.error(ResultCode.PARAM_ERROR, message);
    }

    /**
     * 处理系统异常
     *
     * @param e 系统异常
     * @return 统一响应结果
     */
    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception e) {
        log.error("系统异常：", e);
        return Result.error(ResultCode.BUSINESS_ERROR, ErrorMessage.SYSTEM_ERROR);
    }
} 