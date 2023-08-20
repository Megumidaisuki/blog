package com.megumi.constants;

import lombok.Data;

@Data
public class SystemConstants {
    /*
    文章草稿
     */
    public static final int ARTICLE_STATUS_DRAFT = 1;
    /*
    发布的文章
     */
    public static final int ARTICLE_STATUS_NORMAL = 0;
    /*
    文章不置顶
     */
    public static final int ARTICLE_STATUS_NOT_TOP = 0;
    /*
    文章置顶
     */
    public static final int ARTICLE_STATUS_TOP = 1;
    /*
    文章允许评论
     */
    public static final int ARTICLE_STATUS_COMMENT = 1;
    /*
    文章不允许评论
     */
    public static final int ARTICLE_STATUS_NOT_COMMENT = 0;
    /*
    文章未删除
     */
    public static final int ARTICLE_STATUS_NOT_DELETE = 0;
    /*
    文章已删除
     */
    public static final int ARTICLE_STATUS_DELETE = 1;
    /*
    分类正常状态
     */
    public static final String CATEGORY_STATUS_NORMAL = "0";
    /*
    分类不正常状态
     */
    public static final String CATEGORY_STATUS_NOT_NORMAL = "1";
    /*
    友链审核通过
     */
    public static final String LINK_STATUS_PASS = "0";
    /*
    友链审核未通过
     */
    public static final String LINK_STATUS_NOT_PASS = "1";
    /*
    用户表已删除
     */
    public static final int USER_STATUS_DELETE = 1;
    /*
    用户表未删除
     */
    public static final int USER_STATUS_NOT_DELETE = 0;
    /*
    评论未删除
     */
    public static final int COMMENT_STATUS_NOT_DELETE = 0;
    /*
    评论删除
     */
    public static final int COMMENT_STATUS_DELETE = 1;
    /*
    评论类型：文章
     */
    public static final int COMMENT_TYPE_ARTICLE = 0;
    /*
    评论类型：友链
     */
    public static final int COMMENT_TYPE_LINK = 1;
    /*
    根评论
     */
    public static final int COMMENT_ROOT = -1;

    /*
    图床测试域名
     */
    public static final String TEST_URL = "rxs4g2ziu.hn-bkt.clouddn.com";



}
