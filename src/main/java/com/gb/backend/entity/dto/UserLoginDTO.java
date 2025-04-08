package com.gb.backend.entity.dto;

import com.gb.backend.common.enums.UserRole;
import lombok.Data;


/**
 * 用户登录DTO
 * @since 2024-04-08
 */
@Data
public class UserLoginDTO {
    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 角色
     */
    private UserRole role;
}