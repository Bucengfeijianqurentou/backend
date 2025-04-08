package com.gb.backend.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.gb.backend.common.enums.UserRole;
import lombok.Data;
import java.time.LocalDateTime;

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
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
} 