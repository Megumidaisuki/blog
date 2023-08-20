package com.megumi.controller;

import com.megumi.common.ResponseResult;
import com.megumi.service.SgLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/link")
public class SgLinkController {
    @Autowired
    private SgLinkService sgLinkService;
    @GetMapping("/getAllLink")
    @PreAuthorize("hasAuthority('system:user:get')")
    public ResponseResult getAllLink(){
        return sgLinkService.getAllLink();
    }
}
