package com.megumi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.megumi.common.ResponseResult;
import com.megumi.entity.SysUser;


/**
 * 用户表(SysUser)表数据库访问层
 *
 * @author makejava
 * @since 2023-07-10 09:57:24
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    ResponseResult getUserInfo();
}

