package com.megumi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.megumi.common.ResponseResult;
import com.megumi.entity.SgComment;
import com.megumi.request.AddCommentRequest;
import com.megumi.request.CommentPageRequest;


/**
 * 评论表(SgComment)表服务接口
 *
 * @author makejava
 * @since 2023-07-13 15:51:49
 */
public interface SgCommentService extends IService<SgComment> {

    ResponseResult getCommentList(int commentType, CommentPageRequest commentPageRequest);

    ResponseResult addComment(AddCommentRequest addCommentRequest);
}
