package com.example.backend.controller;


import com.example.backend.common.entity.Result;
import com.example.backend.entity.User;
import com.example.backend.entity.dto.LoginDto;
import com.example.backend.entity.dto.RegisterDto;
import com.example.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author demo
 * @since 2023-11-28
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result register(@RequestBody RegisterDto registerDto) {
        return userService.register(registerDto);
    }

    @PostMapping("/login")
    public Result login(@RequestBody LoginDto loginDto) {
        return userService.login(loginDto);
    }

    @GetMapping("/info")
    public Result getUserInfo(@RequestParam Integer userId) {
        return userService.getUserInfo(userId);
    }

    @GetMapping("/list")
    public Result getUserList() {
        return userService.getUserList();
    }

    @PostMapping("/update")
    public Result updateUserInfo(@RequestBody User user) {
        return userService.updateUserInfo(user);
    }

    @GetMapping("/delete")
    public Result deleteUser(@RequestParam Integer userId) {
        return userService.deleteUser(userId);
    }

    @GetMapping("/find")
    public Result findUser(@RequestParam String keyword) {
        return userService.findUser(keyword);
    }

    @GetMapping("/find/username")
    public Result findUserByUsername(@RequestParam String username) {
        return userService.findUserByUsername(username);
    }


}
