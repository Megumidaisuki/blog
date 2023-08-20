package com.megumi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.megumi.entity.DTO.LinkDTO;
import com.megumi.common.ResponseResult;
import com.megumi.constants.SystemConstants;
import com.megumi.entity.SgLink;
import com.megumi.mapper.SgLinkMapper;
import com.megumi.service.SgLinkService;
import com.megumi.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 友链(SgLink)表服务实现类
 *
 * @author makejava
 * @since 2023-07-10 09:27:21
 */
@Service("sgLinkService")
public class SgLinkServiceImpl extends ServiceImpl<SgLinkMapper, SgLink> implements SgLinkService {
    @Autowired
    private SgLinkService sgLinkService;
    @Override
    //得到所有友链信息
    public ResponseResult getAllLink() {
        LambdaQueryWrapper<SgLink> wrapper= new LambdaQueryWrapper<>();
        wrapper.eq(SgLink::getStatus, SystemConstants.LINK_STATUS_PASS);
        List<SgLink> list = sgLinkService.list(wrapper);
        List<LinkDTO> linkDTOS = BeanCopyUtils.copyProperties(list, LinkDTO.class);
        return ResponseResult.okResult(linkDTOS);
    }
}
