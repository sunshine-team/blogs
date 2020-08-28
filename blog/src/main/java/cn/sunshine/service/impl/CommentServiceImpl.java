package cn.sunshine.service.impl;

import cn.sunshine.dao.CommentRepository;
import cn.sunshine.po.Comment;
import cn.sunshine.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*** @Auther: http://www.bjsxt.com
 * @Date: 2020/1/27 
 * @Description: cn.sunshine.service.impl
 * @version: 1.0 */

/**
 * service层博客评论接口实现类
 */
@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    @Autowired//注入dao层对象
    private CommentRepository commentRepository;

    @Override//根据博客id发布评论
    public List<Comment> listCommentByBlogId(Long blogId) {
        //按创建时间进行排序
        Sort sort = Sort.by(Sort.Direction.ASC,"createTime");
        //返回comments集合
        List<Comment> comments = commentRepository.findByBlogIdAndParentCommentNull(blogId,sort);
        return eachComment(comments);
    }


    @Override//保存评论信息
    public Comment saveComment(Comment comment) {
        //获取父级评论对象的id
        Long parentCommentId = comment.getParentComment().getId();
        //判断父级评论对象的id是否-1
        if (parentCommentId != -1){
            //根据父级发表评论（子级评论）
            comment.setParentComment(commentRepository.getOne(parentCommentId));
        }else{
            //设置父级为空（一级评论）
            comment.setParentComment(null);
        }
        //设置创建时间
        comment.setCreateTime(new Date());
        return commentRepository.save(comment);
    }

    /**
     * 循环每个顶级的评论节点
     * @param comments
     * @return
     */
    private List<Comment> eachComment(List<Comment> comments){
        List<Comment> commentsView = new ArrayList<Comment>();
        for (Comment comment : comments){
            Comment targetComment = new Comment();
            BeanUtils.copyProperties(comment,targetComment);
            commentsView.add(targetComment);
        }
        //合并评论的各层子代到第一级子代集合中
        combineChildren(commentsView);
        return commentsView;
    }

    /**
     *
     * @param comments root根节点，blog不为空的对象集合
     */
    private void combineChildren(List<Comment> comments){
        for (Comment comment : comments){
            List<Comment> replys = comment.getReplyComments();
            for (Comment reply : replys){
                //循环迭代，找出子代，存放在tempReplys中
                recursively(reply);
            }
            //修改顶级节点的reply集合为迭代处理后的集合
            comment.setReplyComments(tempReplys);
            //清除临时存放区
            tempReplys = new ArrayList<>();
        }
    }

    //用于存放迭代找出的所有子代的集合
    private List<Comment> tempReplys = new ArrayList<Comment>();

    /**
     * 递归迭代
     * @param comment 被迭代的对象
     */
    private void recursively(Comment comment){
        //顶级节点添加到临时存放的集合中
        tempReplys.add(comment);
        if (comment.getReplyComments().size()>0){
            List<Comment> replys = comment.getReplyComments();
            for (Comment reply : replys ){
                tempReplys.add(reply);
                if (reply.getReplyComments().size()>0){
                    recursively(reply);
                }
            }
        }
    }
}
