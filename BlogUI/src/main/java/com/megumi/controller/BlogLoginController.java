package com.megumi.controller;

import com.megumi.common.ResponseResult;
import com.megumi.enums.AppHttpCodeEnum;
import com.megumi.exception.SystemException;
import com.megumi.request.LoginRequest;
import com.megumi.service.BlogLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/blog")
public class BlogLoginController {
    @Autowired
    private BlogLoginService blogLoginService;



    @PostMapping("/login")
    public ResponseResult login(@RequestBody LoginRequest loginRequest){
        //在controller层需要对前端输入的值进行校验
        //尽管前端也可以做这件事
        //后端有自己的判断方式是有必要的

        if(!StringUtils.hasText(loginRequest.getUserName())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return blogLoginService.login(loginRequest);
    }

    @PostMapping("/logout")
    public ResponseResult logout(){
        return blogLoginService.logout();
    }

    @GetMapping("/hello")
    @PreAuthorize("hasAuthority('system:user:get')")
    public String hello(){
        return "hello";
    }



}
