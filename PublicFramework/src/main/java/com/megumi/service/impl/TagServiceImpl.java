package com.megumi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.megumi.common.ResponseResult;
import com.megumi.entity.Tag;
import com.megumi.mapper.TagMapper;
import com.megumi.service.TagService;
import org.springframework.stereotype.Service;

/**
 * 标签(Tag)表服务实现类
 *
 * @author makejava
 * @since 2023-08-17 17:18:29
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {


}
