package com.example.backend.service;

import com.example.backend.common.entity.Result;
import com.example.backend.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.backend.entity.dto.LoginDto;
import com.example.backend.entity.dto.RegisterDto;
import org.springframework.stereotype.Service;

/**
 *
 * @author demo
 * @since 2023-11-28
 */
@Service
public interface UserService extends IService<User> {
    Result register(RegisterDto registerDto);
    Result login(LoginDto loginDto);
    Result getUserInfo(Integer userId);
    Result getUserList();
    Result updateUserInfo(User user);
    Result deleteUser(Integer userId);
    Result findUser(String keyword);
    Result findUserByUsername(String username);
}
