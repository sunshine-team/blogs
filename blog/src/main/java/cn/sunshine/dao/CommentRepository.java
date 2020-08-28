package cn.sunshine.dao;

import cn.sunshine.po.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/*** @Auther: http://www.bjsxt.com
 * @Date: 2020/1/27 
 * @Description: cn.sunshine.dao
 * @version: 1.0 */

/**
 * dao层博客评论接口类
 */
public interface CommentRepository extends JpaRepository<Comment,Long> {
    //根据blogId查询博客，并且父级id为空的评论
    public List<Comment> findByBlogIdAndParentCommentNull(Long blogId, Sort sort);
}
