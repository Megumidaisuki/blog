package com.megumi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.megumi.common.ResponseResult;
import com.megumi.entity.SgLink;


/**
 * 友链(SgLink)表服务接口
 *
 * @author makejava
 * @since 2023-07-10 09:27:21
 */
public interface SgLinkService extends IService<SgLink> {

    ResponseResult getAllLink();
}
