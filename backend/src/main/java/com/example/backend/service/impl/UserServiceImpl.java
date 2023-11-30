package com.example.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.backend.common.entity.Result;
import com.example.backend.entity.User;
import com.example.backend.entity.dto.LoginDto;
import com.example.backend.entity.dto.RegisterDto;
import com.example.backend.mapper.UserMapper;
import com.example.backend.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author demo
 * @since 2023-11-28
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public Result register(RegisterDto registerDto) {
        User one = userMapper.selectOne(new QueryWrapper<User>().eq("username", registerDto.getUsername()));
        if (one != null) {
            return Result.fail("Username already exists");
        }
        User user = new User();
        BeanUtils.copyProperties(registerDto, user);
        user.setType("user");
        userMapper.insert(user);
        return Result.succ(user);
    }

    @Override
    public Result login(LoginDto loginDto) {
        User one = userMapper.selectOne(new QueryWrapper<User>().eq("username", loginDto.getUsername()));
        if (one == null) {
            return Result.fail("Username does not exist");
        }
        if (!one.getPassword().equals(loginDto.getPassword())) {
            return Result.fail("Wrong password");
        }
        return Result.succ(one);
    }

    @Override
    public Result getUserInfo(Integer userId) {
        return Result.succ(userMapper.selectById(userId));
    }

    @Override
    public Result getUserList() {
        return Result.succ(userMapper.selectList(null));
    }

    @Override
    public Result updateUserInfo(User user) {
        return Result.succ(userMapper.updateById(user));
    }

    @Override
    public Result deleteUser(Integer userId) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            return Result.fail("User does not exist");
        }
        if (user.getType().equals("admin")) {
            return Result.fail("Cannot delete admin");
        }
        return Result.succ(userMapper.deleteById(userId));
    }

    @Override
    public Result findUser(String keyword) {
        return Result.succ(userMapper.selectList(new QueryWrapper<User>().like("name", keyword)));
    }

    @Override
    public Result findUserByUsername(String username) {
        User one = userMapper.selectOne(new QueryWrapper<User>().eq("username", username));
        return one == null ? Result.fail("User does not exist") : Result.succ(one);
    }
}
