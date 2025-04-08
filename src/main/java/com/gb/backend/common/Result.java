package com.gb.backend.common;

import com.gb.backend.common.constant.ResultCode;
import lombok.Data;

/**
 * 统一响应结果类
 * @since 2024-04-08
 * @param <T> 响应数据类型
 */
@Data
public class Result<T> {
    /** 状态码 */
    private Integer code;
    
    /** 提示信息 */
    private String message;
    
    /** 响应数据 */
    private T data;

    /**
     * 成功响应
     *
     * @param data 响应数据
     * @param <T> 响应数据类型
     * @return Result对象
     */
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(ResultCode.SUCCESS);
        result.setMessage("操作成功");
        result.setData(data);
        return result;
    }

    /**
     * 错误响应
     *
     * @param message 错误信息
     * @param <T> 响应数据类型
     * @return Result对象
     */
    public static <T> Result<T> error(String message) {
        Result<T> result = new Result<>();
        result.setCode(ResultCode.BUSINESS_ERROR);
        result.setMessage(message);
        return result;
    }

    /**
     * 自定义错误响应
     *
     * @param code 错误码
     * @param message 错误信息
     * @param <T> 响应数据类型
     * @return Result对象
     */
    public static <T> Result<T> error(Integer code, String message) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }
} 