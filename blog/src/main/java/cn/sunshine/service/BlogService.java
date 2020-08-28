package cn.sunshine.service;

/*** @Auther: http://www.bjsxt.com 
 * @Date: 2020/1/15 
 * @Description: cn.sunshine.service
 * @version: 1.0 */

import cn.sunshine.po.Blog;
import cn.sunshine.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * service层博客接口类
 */
public interface BlogService {
    //按编号查询博客
    public Blog getBlogById(Long id);
    //获取并转换博客详情
    public Blog getAndConvert(Long id);
    //条件分页查询博客
    public Page<Blog> listBlogByCriteria(Pageable pageable, BlogQuery blogQuery);
    //分页查询博客
    public Page<Blog> listAllBlog(Pageable pageable);
    //分页查询符合对应标签的博客
    public Page<Blog> listBlogByCriteria(Long tagId, Pageable pageable);
    //查询最新博客
    public List<Blog> listRecommendBlogTop(Integer size);
    //按条件查询博客
    public Page<Blog> listBlogByCriteria(String query,Pageable pageable);
    //分组查询博客年份并倒序排列（归档）
    public Map<String,List<Blog>> archivesBlog();
    //查询博客总记录数
    public Long countBlog();
    //新增博客信息
    public Blog saveBlog(Blog blog);
    //修改博客信息
    public Blog updateBlogById(Long id,Blog blog);
    //删除博客信息
    public void deleteBlogById(Long id);
}
