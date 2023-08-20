package com.megumi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.megumi.common.ResponseResult;
import com.megumi.entity.SysUser;
import com.megumi.request.RegisterRequest;
import com.megumi.request.UserUpdateRequest;


/**
 * 用户表(SysUser)表服务接口
 *
 * @author makejava
 * @since 2023-07-10 09:57:24
 */
public interface SysUserService extends IService<SysUser> {

    ResponseResult getUserInfo();

    ResponseResult updateUserInfo(UserUpdateRequest userUpdateRequest);

    ResponseResult register(RegisterRequest registerRequest);
}
