package com.megumi.service.impl;
import com.megumi.entity.DTO.UserInfoDTO;
import com.megumi.entity.DTO.UserLoginDTO;
import com.megumi.common.ResponseResult;
import com.megumi.entity.UserLogin;
import com.megumi.request.LoginRequest;
import com.megumi.service.BlogLoginService;
import com.megumi.utils.BeanCopyUtils;
import com.megumi.utils.JwtUtil;
import com.megumi.utils.RedisCache;
import com.megumi.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class BlogLoginServiceImpl implements BlogLoginService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisCache redisCache;
    @Override
    public ResponseResult login(LoginRequest loginRequest) {
        //SpringSecurity流程
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword());
        //SpringSecurity流程
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //判断是否认证通过
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误");
        }
        //获取id,成token
        UserLogin userLogin = (UserLogin) authenticate.getPrincipal();
        String userId = userLogin.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        //存到redis
        redisCache.setCacheObject("login:"+userId,userLogin);
        //封装返回
        UserInfoDTO userInfoDTO = BeanCopyUtils.copyProperty(userLogin.getUser(), UserInfoDTO.class);
        UserLoginDTO userLoginDTO = new UserLoginDTO(userInfoDTO,jwt);
        return ResponseResult.okResult(userLoginDTO);
    }
    @Override
    public ResponseResult logout() {
        //获取token，解析获取userId
        String userId = SecurityUtils.getUserId().toString();
        //通过userId去redis中查数据
        //删除redis中缓存的数据
        redisCache.deleteObject("login:"+userId);
        return ResponseResult.okResult();

    }
}
