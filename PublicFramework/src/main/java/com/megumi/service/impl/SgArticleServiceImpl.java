package com.megumi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.megumi.entity.DTO.ArticleDetailsDTO;
import com.megumi.entity.DTO.CategoryPageDTO;
import com.megumi.entity.DTO.HotArticleDTO;
import com.megumi.constants.SystemConstants;
import com.megumi.common.ResponseResult;
import com.megumi.entity.DTO.PageDTO;
import com.megumi.entity.SgArticle;
import com.megumi.mapper.SgArticleMapper;
import com.megumi.request.CategoryPageRequest;
import com.megumi.service.SgArticleService;
import com.megumi.service.SgCategoryService;
import com.megumi.utils.BeanCopyUtils;
import com.megumi.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class SgArticleServiceImpl extends ServiceImpl<SgArticleMapper, SgArticle> implements SgArticleService {
@Autowired
private SgCategoryService categoryService;
@Autowired
private RedisCache redisCache;
    @Override
    //查询浏览量最高的十篇文章
    public ResponseResult hotArticleList() {
        LambdaQueryWrapper<SgArticle> wrapper = new LambdaQueryWrapper<SgArticle>();
        //不能是已删除的
        wrapper.eq(SgArticle::getDelFlag, SystemConstants.ARTICLE_STATUS_NOT_DELETE);
        //不能是草稿
        wrapper.eq(SgArticle::getStatus,SystemConstants.ARTICLE_STATUS_NORMAL);
        //降序
        wrapper.orderByDesc(SgArticle::getViewCount);

        //添加page对象
        Page<SgArticle> page = new Page<>(1,10);
        page(page,wrapper);

        //拿到查询数据
        List<SgArticle> articles = page.getRecords();


        //返回数据优化
        List<HotArticleDTO> hotArticleDTOs =  BeanCopyUtils.copyProperties(articles, HotArticleDTO.class);

        return ResponseResult.okResult(hotArticleDTOs);
    }

    @Override
    //分页查询文章接口
    //查询要求:1.只能查询正式发布的文章 2.置顶的文章要写在最前面
    public ResponseResult pageCategory(CategoryPageRequest categoryPageRequest){
        Long categoryId = categoryPageRequest.getCategoryId();
        Integer pageSize = categoryPageRequest.getPageSize();
        Integer pageNum = categoryPageRequest.getPageNum();
        //查询条件
        LambdaQueryWrapper<SgArticle> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 如果 有categoryId 就要 查询时要和传入的相同
        lambdaQueryWrapper.eq(Objects.nonNull(categoryId)&&categoryId>0 ,SgArticle::getCategoryId,categoryId);
        // 状态是正式发布的
        lambdaQueryWrapper.eq(SgArticle::getStatus,SystemConstants.ARTICLE_STATUS_NORMAL);
        // 对isTop进行降序
        lambdaQueryWrapper.orderByDesc(SgArticle::getIsTop);

        //分页查询
        Page<SgArticle> page = new Page<>(pageNum,pageSize);
        page(page,lambdaQueryWrapper);

        List<SgArticle> articles = page.getRecords();
        //查询categoryName
        articles.stream()
                .forEach(article -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getName()));
        //articleId去查询articleName进行设置
//        for (Article article : articles) {
//            Category category = categoryService.getById(article.getCategoryId());
//            article.setCategoryName(category.getName());
//        }
        page.setRecords(articles);
        //封装查询结果
        List<CategoryPageDTO> articleListVos = BeanCopyUtils.copyProperties(page.getRecords(), CategoryPageDTO.class);

        PageDTO pageDTO = new PageDTO(articleListVos,page.getTotal());
        return ResponseResult.okResult(pageDTO);
    }

    @Override
    //查询文章详细信息，包括分类名
    public ResponseResult getArticleDetails(Long id) {
        SgArticle article = getById(id);
        //从缓存中拿到浏览次数
        article.setViewCount(getViewCount(id));
        article.setCategoryName(categoryService.getById(article.getCategoryId()).getName());
        ArticleDetailsDTO dto = BeanCopyUtils.copyProperty(article, ArticleDetailsDTO.class);
        return ResponseResult.okResult(dto);
    }


    //从redis读取浏览次数
    public Long getViewCount(Long id){
        Map<String,Integer> articles = redisCache.getCacheObject("article::viewCount");
        return Long.valueOf(articles.get(id));
    }

    @Override
    //浏览量加一
    public ResponseResult updateViewCount(Integer id) {
        //在redis缓存中拿到数据
        Map<String,Integer> articles = redisCache.getCacheObject("article::viewCount");
        //加一
        articles.put(String.valueOf(id),articles.get(String.valueOf(id))+1);
        redisCache.setCacheObject("article::viewCount",articles);
        return ResponseResult.okResult();
    }
}
