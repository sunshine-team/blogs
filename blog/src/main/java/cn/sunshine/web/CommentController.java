package cn.sunshine.web;

/*** @Auther: http://www.bjsxt.com 
 * @Date: 2020/1/25 
 * @Description: cn.sunshine.web
 * @version: 1.0 */

import cn.sunshine.po.Blog;
import cn.sunshine.po.Comment;
import cn.sunshine.po.User;
import cn.sunshine.service.BlogService;
import cn.sunshine.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 用户评论
 */
@Controller
public class CommentController {

    @Autowired//注入service层对象
    public CommentService commentService;

    @Autowired//注入service层对象
    public BlogService blogService;

    @Value("${comment.avatar}")//注入访客头像
    private String avatar;

    /**
     * 展示评论信息列表
     * @param blogId
     * @param model
     * @return
     */
    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model){
        //根据博客ID发表评论
        List<Comment> comments = commentService.listCommentByBlogId(blogId);
        //设置模型
        model.addAttribute("comments",comments);
        return "blog :: commentList";
    }

    /**
     * 保存评论信息
     * @param comment
     * @return
     */
    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session){
        //获取blogId
        Long blogId = comment.getBlog().getId();
        //根据blogId获取博客对象
        Blog blog = blogService.getBlogById(blogId);
        //设置评论类中blog对象的值
        comment.setBlog(blog);
        //获取当前用户对象
        User user= (User)session.getAttribute("user");
        //判断该用户是否登录
        if (user != null){
            //该用户已登录
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
        }else{
            //该用户未登录
            comment.setAvatar(avatar);
        }
        //保存评论信息
        commentService.saveComment(comment);
        return "redirect:/comments/"+blogId;
    }
}
