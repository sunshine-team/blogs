package cn.sunshine.service;

/*** @Auther: http://www.bjsxt.com 
 * @Date: 2020/1/27 
 * @Description: cn.sunshine.service
 * @version: 1.0 */

import cn.sunshine.po.Comment;

import java.util.List;

/**
 * service层博客评论接口类
 */
public interface CommentService {
    //根据博客id发布评论
    public List<Comment> listCommentByBlogId(Long blogId);
    //保存评论信息
    public Comment saveComment(Comment comment);
}
