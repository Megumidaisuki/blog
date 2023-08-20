package com.megumi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.megumi.common.ResponseResult;
import com.megumi.entity.SgArticle;
import com.megumi.request.CategoryPageRequest;

public interface SgArticleService extends IService<SgArticle> {
    ResponseResult hotArticleList();


    ResponseResult pageCategory(CategoryPageRequest categoryPageRequest);

    ResponseResult getArticleDetails(Long id);

    ResponseResult updateViewCount(Integer id);
}
