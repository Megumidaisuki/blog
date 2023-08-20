package com.megumi.controller;

import com.megumi.common.ResponseResult;
import com.megumi.constants.SystemConstants;
import com.megumi.request.AddCommentRequest;
import com.megumi.request.CommentPageRequest;
import com.megumi.service.SgCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private SgCommentService commentService;

    @GetMapping("/commentList")
    public ResponseResult getCommentList(CommentPageRequest commentPageRequest){
        return commentService.getCommentList(SystemConstants.COMMENT_TYPE_ARTICLE,commentPageRequest);
    }
    @PostMapping("/addComment")
    public ResponseResult addComment(@RequestBody AddCommentRequest addCommentRequest){
        return commentService.addComment(addCommentRequest);
    }
    @GetMapping("/linkCommentList")
    public ResponseResult getLinkCommentList(CommentPageRequest commentPageRequest){
        return commentService.getCommentList(SystemConstants.COMMENT_TYPE_LINK, commentPageRequest);
    }

}
