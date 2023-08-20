package com.megumi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.megumi.entity.Menu;

import java.util.List;

public interface MenuMapper extends BaseMapper<Menu> {
    List<String> selectPermsByUserId(Long userId);
}
