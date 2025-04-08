package com.gb.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gb.backend.entity.dto.UserLoginDTO;
import com.gb.backend.entity.dto.UserRegisterDTO;
import com.gb.backend.entity.User;

public interface UserService extends IService<User> {
    // 可以添加自定义的业务方法
    User findByUsername(String username);
    User login(UserLoginDTO loginDTO);
    User register(UserRegisterDTO registerDTO);
} 