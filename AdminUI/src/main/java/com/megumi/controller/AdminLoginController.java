package com.megumi.controller;

import com.megumi.common.ResponseResult;
import com.megumi.request.LoginRequest;
import com.megumi.service.AdminLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminLoginController {
    @Autowired
    private AdminLoginService adminLoginService;
    @PostMapping("login")
    public ResponseResult login(@RequestBody LoginRequest loginRequest){
        return adminLoginService.login(loginRequest);
    }
    @PostMapping("/logout")
    public ResponseResult logout(){
        return adminLoginService.logout();
    }


}
