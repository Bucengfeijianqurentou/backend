package com.gb.backend.controller.dto;

import com.gb.backend.entity.User;
import lombok.Data;

/**
 * 用户登录响应DTO
 *
 * @author Claude
 * @since 2024-04-08
 */
@Data
public class UserLoginVO {
    /**
     * 用户信息
     */
    private User user;
    
    /**
     * JWT token
     */
    private String token;

    public UserLoginVO(User user, String token) {
        this.user = user;
        this.token = token;
    }
} 