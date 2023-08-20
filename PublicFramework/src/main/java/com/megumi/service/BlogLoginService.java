package com.megumi.service;

import com.megumi.common.ResponseResult;
import com.megumi.request.LoginRequest;

public interface BlogLoginService {
    ResponseResult login(LoginRequest loginRequest);

    ResponseResult logout();
}
