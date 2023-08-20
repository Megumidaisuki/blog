package com.megumi.service;

import com.megumi.common.ResponseResult;
import com.megumi.request.LoginRequest;

public interface AdminLoginService {
    ResponseResult login(LoginRequest loginRequest);

    ResponseResult logout();
}
