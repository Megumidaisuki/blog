package com.megumi.runner;
import com.megumi.entity.SgArticle;
import com.megumi.mapper.SgArticleMapper;
import com.megumi.service.SgArticleService;
import com.megumi.utils.RedisCache;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


//项目启动时便加载的代码
@Component
public class ViewCountRunner implements CommandLineRunner {
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private SgArticleMapper sgArticleMapper;

    //在启动时运行，且只会运行一次：将文章的浏览量存到redis里面去
    @Override
    public void run(String... args) throws Exception {
        //查询文章信息
        List<SgArticle> articles = sgArticleMapper.selectList(null);
        //制作map集合存入redis
        Map<String, Integer> ViewCountMap = articles.stream()
                .collect(Collectors.toMap(sgArticle -> sgArticle.getId().toString(), sgArticle -> sgArticle.getViewCount().intValue()));
        //存入redis
        redisCache.setCacheObject("article::viewCount", ViewCountMap);
    }
}
