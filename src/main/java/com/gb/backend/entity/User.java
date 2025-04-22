package com.gb.backend.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gb.backend.common.enums.UserRole;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 用户实体类
 *
 * @since 2024-04-08
 */
@Data
@TableName("users")
public class User {
    @TableId(type = IdType.AUTO)
    private Integer id;
    
    private String username;
    private String password;
    
    @EnumValue
    private UserRole role;
    
    private String phone;
    private String email;

    private String realName;

    private String chainAccount;

    private Integer status;
    
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;
} 