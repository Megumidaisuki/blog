package com.megumi.controller;

import com.megumi.annotation.SystemLog;
import com.megumi.common.ResponseResult;
import com.megumi.mapper.SysUserMapper;
import com.megumi.request.RegisterRequest;
import com.megumi.request.UserUpdateRequest;
import com.megumi.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private SysUserService userService;

    @GetMapping("/userInfo")
    public ResponseResult getUserInfo(){
        return userService.getUserInfo();
    }

    @PutMapping("/userInfo")
    @SystemLog(businessName = "更新用户信息")
    public ResponseResult updateUserInfo(@RequestBody UserUpdateRequest userUpdateRequest){
        return userService.updateUserInfo(userUpdateRequest);

    }
    @PostMapping("/register")
    public ResponseResult register(@Validated @RequestBody RegisterRequest registerRequest){
        return userService.register(registerRequest);
    }
}
