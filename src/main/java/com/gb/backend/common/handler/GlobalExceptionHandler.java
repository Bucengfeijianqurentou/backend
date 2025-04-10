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
import org.springframework.web.servlet.resource.NoResourceFoundException;

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
     * 处理资源未找到异常
     */
    @ExceptionHandler(NoResourceFoundException.class)
    public Result<Void> handleNoResourceFoundException(NoResourceFoundException e) {
        // 如果是favicon.ico的请求，直接返回空结果，不记录错误日志
        if (e.getMessage().contains("favicon.ico")) {
            return Result.error("Resource not found");
        }
        // 其他资源未找到的情况，记录错误日志
        log.error("资源未找到：{}", e.getMessage());
        return Result.error("Resource not found: " + e.getMessage());
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