package com.megumi.controller;

import com.megumi.common.ResponseResult;
import com.megumi.request.CategoryPageRequest;
import com.megumi.service.SgArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
public class SgArticleController {
    @Autowired
    private SgArticleService articleService;

    @GetMapping("/hotArticleList")
    public ResponseResult hotArticleList() {
        ResponseResult result = articleService.hotArticleList();
        return result;
    }
    @GetMapping("/pageCategory")
    public ResponseResult pageCategory(CategoryPageRequest categoryPageRequest) {
        return articleService.pageCategory(categoryPageRequest);
    }
    @GetMapping("/{id}")
    public ResponseResult getArticleDetails(@PathVariable Long id){

        return articleService.getArticleDetails(id);
    }
    @PutMapping("/updateViewCount/{id}")
    public ResponseResult updateViewCount(@PathVariable Integer id){
        return articleService.updateViewCount(id);
    }

}
