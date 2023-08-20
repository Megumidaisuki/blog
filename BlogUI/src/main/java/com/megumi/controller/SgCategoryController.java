package com.megumi.controller;

import com.megumi.common.ResponseResult;
import com.megumi.service.SgCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
public class SgCategoryController {
    @Autowired
    private SgCategoryService categoryService;

    @GetMapping("/categoryList")
    public ResponseResult categoryList() {
        return categoryService.categoryList();
    }

}
