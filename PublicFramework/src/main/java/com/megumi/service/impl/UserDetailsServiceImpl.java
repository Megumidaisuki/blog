package com.megumi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.megumi.constants.SystemConstants;
import com.megumi.entity.SysUser;
import com.megumi.entity.UserLogin;
import com.megumi.mapper.MenuMapper;
import com.megumi.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private SysUserMapper userMapper;
    @Autowired

    private MenuMapper menuMapper;
    @Override
    //该方法默认查询内存中，通过重写使其查询数据库
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据用户名查询用户信息
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUserName,username);
        wrapper.eq(SysUser::getDelFlag, SystemConstants.USER_STATUS_NOT_DELETE);
        SysUser user = userMapper.selectOne(wrapper);
        //判断是否查到用户，没查到抛出异常
        if(Objects.isNull(user)){
            throw new RuntimeException("用户不存在");
        }
        //返回用户信息
        //TODO 查询权限信息封装
        List<String> list = menuMapper.selectPermsByUserId(user.getId());
        return new UserLogin(user,list);
    }
}
