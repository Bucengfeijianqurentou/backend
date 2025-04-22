package com.gb.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gb.backend.chain.service.WeBASEService;
import com.gb.backend.common.constant.ErrorMessage;
import com.gb.backend.common.exception.BusinessException;
import com.gb.backend.entity.dto.UserLoginDTO;
import com.gb.backend.entity.dto.UserRegisterDTO;
import com.gb.backend.entity.User;
import com.gb.backend.mapper.UserMapper;
import com.gb.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

/**
 * 用户服务实现类
 * 实现用户相关的业务逻辑，包括登录、注册、查询等操作
 *
 * @author Claude
 * @since 2024-04-08
 */
@RequiredArgsConstructor
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final WeBASEService weBASEService;
    
    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户信息
     */
    @Override
    public User findByUsername(String username) {
        return getOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, username));
    }

    /**
     * 用户登录
     * 验证用户名、密码和角色是否匹配
     *
     * @param loginDTO 登录参数
     * @return 登录成功的用户信息
     * @throws BusinessException 当用户名或密码错误，或角色不匹配时
     */
    @Override
    public User login(UserLoginDTO loginDTO) {
        // 对密码进行MD5加密
        String encryptedPassword = DigestUtils.md5DigestAsHex(loginDTO.getPassword().getBytes());
        
        // 构建查询条件
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, loginDTO.getUsername())
                   .eq(User::getPassword, encryptedPassword)
                   .eq(User::getRole, loginDTO.getRole());
        
        // 查询用户
        User user = this.getOne(queryWrapper);
        if (user == null) {
            throw new BusinessException(ErrorMessage.USERNAME_PASSWORD_ROLE_ERROR);
        }

        if (user.getStatus() == 1) {
            throw new BusinessException(ErrorMessage.STATUS_ERROR);
        }
        return user;
    }

    /**
     * 用户注册
     * 创建新用户，包括用户名查重、密码加密等操作
     *
     * @param registerDTO 注册参数
     * @return 注册成功的用户信息
     * @throws BusinessException 当用户名已存在或角色为空时
     */
    @Override
    public User register(UserRegisterDTO registerDTO) {
        // 检查用户名是否已存在
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, registerDTO.getUsername());
        if (this.count(queryWrapper) > 0) {
            throw new BusinessException(ErrorMessage.USERNAME_EXISTS);
        }
        
        // 验证角色是否设置
        if (registerDTO.getRole() == null) {
            throw new BusinessException(ErrorMessage.ROLE_NOT_NULL);
        }
        
        // 创建新用户
        User user = new User();
        BeanUtils.copyProperties(registerDTO, user);
        
        // 加密密码
        user.setPassword(DigestUtils.md5DigestAsHex(registerDTO.getPassword().getBytes()));

        //注册区块链账户
        String address = weBASEService.registerChainCount(user.getUsername());
        user.setChainAccount(address);

        //设置默认状态：1（待审核）
        user.setStatus(1);

        // 保存用户
        this.save(user);
        return user;
    }
} 