package com.megumi.service.impl;

import com.google.gson.Gson;
import com.megumi.common.ResponseResult;
import com.megumi.constants.SystemConstants;
import com.megumi.enums.AppHttpCodeEnum;
import com.megumi.exception.SystemException;
import com.megumi.service.UploadService;
import com.megumi.utils.PathUtils;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;

@Service
@ConfigurationProperties(prefix = "oss")
@Data
public class UploadServiceImpl implements UploadService {

    private String accessKey;
    private String secretKey;
    private String bucket;

    @Override
    public ResponseResult upload(MultipartFile image) throws IOException {
        //TODO 判断文件名字或者文件大小

        String originalFilename = image.getOriginalFilename();

        if (!originalFilename.endsWith(".png") && !originalFilename.endsWith(".jpg")) {
            throw new SystemException(AppHttpCodeEnum.FILE_FORMAT_FALSE);
        }


        //如果判断通过上传文件到OSS
        String filename = PathUtils.generateFilePath(originalFilename);
        return ResponseResult.okResult(uploadOss(image,filename));
    }

    public String uploadOss(MultipartFile image,String filename) throws IOException {

        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.autoRegion());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
        UploadManager uploadManager = new UploadManager(cfg);

        //从传进来的Multipart对象中获得输入流
        InputStream inputStream = image.getInputStream();

        //指定文件名
        String key = filename;
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(inputStream, key, upToken, null, null);

            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
            return "http://" + SystemConstants.TEST_URL + "/" + filename;

        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
        return "fff";
    }
}
