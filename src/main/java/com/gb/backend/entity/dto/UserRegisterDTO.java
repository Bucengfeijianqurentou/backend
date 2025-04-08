package com.gb.backend.entity.dto;

import com.gb.backend.common.enums.UserRole;
import lombok.Data;

@Data
public class UserRegisterDTO {
    private String username;
    private String password;
    private String email;
    private String phone;
    private String realName;
    private UserRole role;  // 默认不设置，由前端传入
} 