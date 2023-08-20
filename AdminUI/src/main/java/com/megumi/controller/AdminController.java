package com.megumi.controller;

import com.megumi.common.ResponseResult;
import com.megumi.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    //得到详细用户信息
    @GetMapping("/getInfo")
    public ResponseResult getInfo(){
        return adminService.getInfo();
    }

    //得到详细权限菜单信息
    @GetMapping("/getRouters")
    public ResponseResult getRouters(){
        return adminService.getRouters();
    }

}
