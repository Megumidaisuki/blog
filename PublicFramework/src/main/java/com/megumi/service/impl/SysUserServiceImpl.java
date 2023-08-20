package com.megumi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.megumi.entity.DTO.UserInfoDTO;
import com.megumi.common.ResponseResult;
import com.megumi.entity.SysUser;
import com.megumi.enums.AppHttpCodeEnum;
import com.megumi.exception.SystemException;
import com.megumi.mapper.SysUserMapper;
import com.megumi.request.RegisterRequest;
import com.megumi.request.UserUpdateRequest;
import com.megumi.service.SysUserService;
import com.megumi.utils.BeanCopyUtils;
import com.megumi.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 用户表(SysUser)表服务实现类
 *
 * @author makejava
 * @since 2023-07-10 09:57:24
 */
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public ResponseResult getUserInfo() {
        //此处先查id，再到数据库中查询用户信息，就是防止用户修改信息，而SecurityContextHolder中的信息没有变化
        Long userId = SecurityUtils.getUserId();
        SysUser sysUser = getById(userId);
        UserInfoDTO userInfoDTO = BeanCopyUtils.copyProperty(sysUser, UserInfoDTO.class);
        return ResponseResult.okResult(userInfoDTO);
    }

    @Override
    public ResponseResult updateUserInfo(UserUpdateRequest userUpdateRequest) {
        //解释一下更新操作的小要点：
        //上传头像在选择的时候该接口就已经被调用，返回链接
        //最后保存到数据库的时候，链接再和其他信息一起存到数据库
        //两次操作是异步的，能加快效率
        LambdaUpdateWrapper<SysUser> wrapper = new LambdaUpdateWrapper();
        wrapper.eq(SysUser::getId,userUpdateRequest.getId());
        wrapper.set(SysUser::getNickName,userUpdateRequest.getNickName());
        wrapper.set(SysUser::getAvatar,userUpdateRequest.getAvatar());
        wrapper.set(SysUser::getSex,userUpdateRequest.getSex());
        sysUserService.update(null,wrapper);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult register(RegisterRequest registerRequest) {
//        //非空判断
//        if(!StringUtils.hasText(registerRequest.getUserName())){
//            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
//        }else if(!StringUtils.hasText(registerRequest.getPassword())){
//            throw new SystemException(AppHttpCodeEnum.PASSWORD_NULL);
//        }else if(!StringUtils.hasText(registerRequest.getEmail())){
//            throw new SystemException(AppHttpCodeEnum.EMAIL_NULL);
//        }else if(!StringUtils.hasText(registerRequest.getNickName())){
//            throw new SystemException(AppHttpCodeEnum.NICKNAME_NULL);
//        }

        //存在性判断
        if(judgeExistence(registerRequest.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if(judgeExistence(registerRequest)){
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }

        //对密码进行加密
        String encode = passwordEncoder.encode(registerRequest.getPassword());
        registerRequest.setPassword(encode);
        SysUser user = BeanCopyUtils.copyProperty(registerRequest, SysUser.class);

        //存入数据库
        save(user);
        return ResponseResult.okResult();
    }
    //判断用户名存在性的工具方法
    private boolean judgeExistence(String username){
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUserName,username);
        return count(wrapper)>0;
    }
    private boolean judgeExistence(RegisterRequest registerRequest){
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getEmail,registerRequest.getEmail());
        return count(wrapper)>0;
    }

}
