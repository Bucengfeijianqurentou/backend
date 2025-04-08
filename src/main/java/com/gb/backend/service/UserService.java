package com.gb.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gb.backend.entity.User;

public interface UserService extends IService<User> {
    // 可以添加自定义的业务方法
    User findByUsername(String username);
} 