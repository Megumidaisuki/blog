package com.megumi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.megumi.entity.DTO.CommentDTO;
import com.megumi.entity.DTO.PageDTO;
import com.megumi.common.ResponseResult;
import com.megumi.constants.SystemConstants;
import com.megumi.entity.SgComment;
import com.megumi.enums.AppHttpCodeEnum;
import com.megumi.exception.SystemException;
import com.megumi.mapper.SgCommentMapper;
import com.megumi.request.AddCommentRequest;
import com.megumi.request.CommentPageRequest;
import com.megumi.service.SgCommentService;
import com.megumi.service.SysUserService;
import com.megumi.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 评论表(SgComment)表服务实现类
 *
 * @author makejava
 * @since 2023-07-13 15:51:49
 */
@Service("sgCommentService")
public class SgCommentServiceImpl extends ServiceImpl<SgCommentMapper, SgComment> implements SgCommentService {

    @Autowired
    private SgCommentService commentService;
    @Autowired
    private SysUserService userService;

    @Override
    public ResponseResult getCommentList(int commentType, CommentPageRequest commentPageRequest) {
        //查询对应的的根评论
        LambdaQueryWrapper<SgComment> wrapper = new LambdaQueryWrapper<>();
        //判断是友链评论还是文章评论
        if (commentType == SystemConstants.COMMENT_TYPE_LINK && commentPageRequest.getArticleId() == null) {
            //友链根评论
            wrapper.eq(SgComment::getType, SystemConstants.COMMENT_TYPE_LINK);
        } else {
            //文章根评论
            wrapper.eq(SgComment::getType, SystemConstants.COMMENT_TYPE_ARTICLE);
        }
        wrapper.eq(SgComment::getDelFlag, SystemConstants.COMMENT_STATUS_NOT_DELETE);
        wrapper.eq(SgComment::getRootId, SystemConstants.COMMENT_ROOT);

        //拿到根评论
        Page<SgComment> page = new Page<>(commentPageRequest.getPageNum(), commentPageRequest.getPageSize());
        page(page, wrapper);
        List<SgComment> pageRecords = page.getRecords();
        List<CommentDTO> commentDTOS = transferComments(pageRecords);
        for (CommentDTO commentDTO : commentDTOS) {
            List<SgComment> sonById = getSonById(commentDTO.getId());
            commentDTO.setChildren(transferComments(sonById));
        }
        PageDTO pageDTO = new PageDTO(commentDTOS, page.getTotal());
        return ResponseResult.okResult(pageDTO);
    }

    @Override
    public ResponseResult addComment(AddCommentRequest addCommentRequest) {
        //评论内容不能为空
        if (!StringUtils.hasText(addCommentRequest.getContent())) {
            throw new SystemException(AppHttpCodeEnum.COMMENT_NULL);
        }

        //mp配置的自动填充
        SgComment sgComment = BeanCopyUtils.copyProperty(addCommentRequest, SgComment.class);
        save(sgComment);
        return ResponseResult.okResult();
    }

    //工具方法：getCommentList————封装一个根据根id查到子评论的过程
    public List<SgComment> getSonById(Long id) {
        LambdaQueryWrapper<SgComment> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.eq(SgComment::getRootId, id);
        List<SgComment> list = list(wrapper1);
        return list;
    }

    //工具方法：getCommentList————封装将CommentDTOs赋值的一个过程，以便反复使用
    public List<CommentDTO> transferComments(List<SgComment> comments) {
        List<CommentDTO> commentDTOS = BeanCopyUtils.copyProperties(comments, CommentDTO.class);
        commentDTOS = commentDTOS.stream().map((CommentDTO commentDTO) -> {
            if (commentDTO.getToCommentUserId() != -1) {
                commentDTO.setToCommentUserName(userService.getById(commentDTO.getToCommentUserId()).getNickName());
            }
            commentDTO.setUsername(userService.getById(commentDTO.getCreateBy()).getNickName());

            return commentDTO;
        }).collect(Collectors.toList());
        return commentDTOS;
    }
}
