package com.gb.backend.common.constant;
/**
 * 响应状态码常量
 * @since 2024-04-08
 */
public interface ResultCode {
    /** 成功 */
    Integer SUCCESS = 200;
    /** 参数错误 */
    Integer PARAM_ERROR = 400;
    /** 未授权 */
    Integer UNAUTHORIZED = 401;
    /** 禁止访问 */
    Integer FORBIDDEN = 403;
    /** 资源未找到 */
    Integer NOT_FOUND = 404;
    /** 业务错误 */
    Integer BUSINESS_ERROR = 500;
} 