package com.megumi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.megumi.common.ResponseResult;
import com.megumi.entity.SgCategory;


/**
 * 分类表(SgCategory)表服务接口
 *
 * @author makejava
 * @since 2023-07-08 11:56:05
 */
public interface SgCategoryService extends IService<SgCategory> {

    ResponseResult categoryList();
}
