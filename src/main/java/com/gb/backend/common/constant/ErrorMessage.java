package com.gb.backend.common.constant;
/**
 * 错误信息常量
 * 统一管理系统错误提示信息
 * @since 2024-04-08
 */
public interface ErrorMessage {
    /** 用户名或密码错误提示 */
    String USERNAME_PASSWORD_ERROR = "用户名或密码错误";

    /** 用户名已存在提示 */
    String USERNAME_EXISTS = "用户名已存在";

    /** 用户角色为空提示 */
    String ROLE_NOT_NULL = "用户角色不能为空";

    /** 参数错误提示 */
    String PARAM_ERROR = "参数错误";

    /** 系统错误提示 */
    String SYSTEM_ERROR = "系统错误";
} 