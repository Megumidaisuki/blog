package com.megumi.job;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.megumi.constants.SystemConstants;
import com.megumi.entity.SgArticle;
import com.megumi.service.SgArticleService;
import com.megumi.utils.RedisCache;
import com.megumi.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

//定时任务
@Component
public class UpdateViewCountJob {
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private SgArticleService sgArticleService;
    @Scheduled(cron = "0 */10 * * * ?")
    //每十分钟更新一次数据
    public void updateViewCount(){
        Map<String,Integer> articles =redisCache.getCacheObject("article::viewCount");
        List<SgArticle> list = sgArticleService.list();

        for(SgArticle sgArticle : list) {
            Integer viewCount = articles.get(sgArticle.getId());
            //将缓存的数据存入数据库
            LambdaUpdateWrapper<SgArticle> wrapper = new LambdaUpdateWrapper<>();
            wrapper.eq(SgArticle::getId, sgArticle.getId());
            wrapper.eq(SgArticle::getDelFlag, SystemConstants.ARTICLE_STATUS_NOT_DELETE);
            wrapper.set(SgArticle::getViewCount, viewCount);
            sgArticleService.update(wrapper);
        }
    }
}
