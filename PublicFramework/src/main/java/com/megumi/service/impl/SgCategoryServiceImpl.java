package com.megumi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.megumi.entity.DTO.CategoryDTO;
import com.megumi.common.ResponseResult;
import com.megumi.constants.SystemConstants;
import com.megumi.entity.SgArticle;
import com.megumi.entity.SgCategory;
import com.megumi.mapper.SgArticleMapper;
import com.megumi.mapper.SgCategoryMapper;
import com.megumi.service.SgCategoryService;
import com.megumi.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 分类表(SgCategory)表服务实现类
 *
 * @author makejava
 * @since 2023-07-08 11:56:05
 */
@Service("sgCategoryService")
public class SgCategoryServiceImpl extends ServiceImpl<SgCategoryMapper, SgCategory> implements SgCategoryService {

    @Autowired
    private SgArticleMapper sgArticleMapper;
    
    @Override
    //展示分类列表，用户可以点击具体的分类查看该分类下的文章列表
    public ResponseResult categoryList() {
        //出于互联网公司规范，三个以上的联表查询不被允许，因此我采用两次单表查询来实现功能

        //首先在Article表中将满足正式发文章的分类id查询出来
        LambdaQueryWrapper<SgArticle> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SgArticle::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL)
                .eq(SgArticle::getDelFlag,SystemConstants.ARTICLE_STATUS_NOT_DELETE);
        List<SgArticle> sgArticles = sgArticleMapper.selectList(wrapper);
        Set<Long> ids = sgArticles.stream()
                .map(SgArticle::getCategoryId)
                .collect(Collectors.toSet());
        //通过查出来的id去category表里面匹配
        List<SgCategory> sgCategories = listByIds(ids);
        List<SgCategory> list = sgCategories.stream()
                .filter(sgCategory -> sgCategory.getStatus().equals(SystemConstants.CATEGORY_STATUS_NORMAL))
                .collect(Collectors.toList());

        //返回数据优化
        List<CategoryDTO> categoryDTOS = BeanCopyUtils.copyProperties(list, CategoryDTO.class);
        return ResponseResult.okResult(categoryDTOS);
    }
}
