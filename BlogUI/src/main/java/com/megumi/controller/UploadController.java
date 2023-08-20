package com.megumi.controller;

import com.megumi.common.ResponseResult;
import com.megumi.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class UploadController {
    @Autowired
    private UploadService uploadService;
    @PostMapping("/upload ")
    public ResponseResult upload(@RequestBody MultipartFile image) throws IOException {
            return uploadService.upload(image);
    }
}

