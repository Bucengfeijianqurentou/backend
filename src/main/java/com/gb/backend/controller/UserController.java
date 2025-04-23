package com.gb.backend.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gb.backend.common.Result;
import com.gb.backend.entity.vo.UserLoginVO;
import com.gb.backend.entity.User;
import com.gb.backend.entity.dto.UserLoginDTO;
import com.gb.backend.entity.dto.UserRegisterDTO;
import com.gb.backend.service.UserService;
import com.gb.backend.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户控制器
 * 处理用户相关的请求，包括登录、注册、查询、修改等操作
 * @since 2024-04-08
 */
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;
    private final JwtUtil jwtUtil;

    /**
     * 用户登录
     *
     * @param loginDTO 登录参数
     * @return 登录成功的用户信息和token
     */
    @PostMapping("/login")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO loginDTO) {
        User user = userService.login(loginDTO);
        String token = jwtUtil.generateToken(user);
        return Result.success(new UserLoginVO(user, token));
    }

    /**
     * 用户注册
     *
     * @param registerDTO 注册参数
     * @return 注册成功的用户信息
     */
    @PostMapping("/register")
    public Result<User> register(@RequestBody UserRegisterDTO registerDTO) {
        User user = userService.register(registerDTO);
        return Result.success(user);
    }

    /**
     * 分页查询用户列表
     *
     * @param page 页码
     * @param size 每页大小
     * @return 分页用户数据
     */
    @GetMapping
    public Page<User> list(@RequestParam(defaultValue = "1") int page,
                          @RequestParam(defaultValue = "10") int size) {
        return userService.page(new Page<>(page, size));
    }

    /**
     * 根据ID查询用户
     *
     * @param id 用户ID
     * @return 用户信息
     */
    @GetMapping("/{id}")
    public User getById(@PathVariable Integer id) {
        return userService.getById(id);
    }

    /**
     * 新增用户
     *
     * @param user 用户信息
     * @return 是否成功
     */
    @PostMapping
    public boolean save(@RequestBody User user) {
        return userService.save(user);
    }

    /**
     * 更新用户信息
     *
     * @param id 用户ID
     * @param user 用户信息
     * @return 是否成功
     */
    @PutMapping("/{id}")
    public boolean update(@PathVariable Integer id, @RequestBody User user) {
        user.setId(id);
        return userService.updateById(user);
    }

    /**
     * 删除用户
     *
     * @param id 用户ID
     * @return 是否成功
     */
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id) {
        return userService.removeById(id);

    }

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户信息
     */
    @GetMapping("/username/{username}")
    public User getByUsername(@PathVariable String username) {
        return userService.findByUsername(username);
    }

    /**
     * 获取当前登录用户信息
     * @return 当前登录用户信息
     */
    @GetMapping("/info")
    public Result<User> getCurrentUser() {
        // 从 JWT 中获取用户名
        String username = jwtUtil.getCurrentUsername();
        User user = userService.findByUsername(username);
        return Result.success(user);
    }
    
    /**
     * 切换用户状态（启用/禁用）
     * @param id 用户ID
     * @param status 目标状态：0-启用，1-禁用
     * @return 是否成功
     */
    @PutMapping("/{id}/status/{status}")
    public Result<Boolean> toggleStatus(@PathVariable Integer id, @PathVariable Integer status) {
        User user = userService.getById(id);
        if (user == null) {
            return Result.error(404, "用户不存在");
        }
        
        user.setStatus(status);
        boolean result = userService.updateById(user);
        return Result.success(result);
    }
    
    /**
     * 获取系统用户总数
     * @return 用户总数
     */
    @GetMapping("/count")
    public Result<Map<String, Object>> getUserCount() {
        int count = userService.getUserCount();
        Map<String, Object> data = new HashMap<>();
        data.put("count", count);
        return Result.success(data);
    }
} 