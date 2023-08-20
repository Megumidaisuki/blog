package com.megumi.service;

import com.megumi.common.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadService {
    ResponseResult upload(MultipartFile image) throws IOException;
}
