package cn.sunshine.service.impl;

/*** @Auther: http://www.bjsxt.com 
 * @Date: 2020/1/15 
 * @Description: cn.sunshine.service.impl
 * @version: 1.0 */

import cn.sunshine.NotFoundException;
import cn.sunshine.po.Blog;
import cn.sunshine.po.Type;
import cn.sunshine.dao.BlogRepository;
import cn.sunshine.service.BlogService;
import cn.sunshine.util.MarkdownUtils;
import cn.sunshine.util.MyBeanUtils;
import cn.sunshine.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.*;

/**
 * service层博客接口实现类
 */
@Service
@Transactional
public class BlogServiceImpl implements BlogService {

    @Autowired//注入dao层对象
    private BlogRepository blogRepository;

    @Override//按编号查询博客
    public Blog getBlogById(Long id) {
        return blogRepository.getOne(id);
    }

    @Override //获取并转换博客详情
    public Blog getAndConvert(Long id) {
        //根据id获取博客信息
        Blog blog = blogRepository.getOne(id);
        //判断该博客对象是否为空
        if(blog==null){
            throw new NotFoundException("该博客不存在！");
        }
        //创建一个Blog对象
        Blog targetBlog = new Blog();
        //将blog对象中的值复制到targetBlog对象中
        BeanUtils.copyProperties(blog,targetBlog);
        //获取该博客的详情信息
        String content = targetBlog.getContent();
        //(MarkdownUtils.markdownToHtmlExtensions(content)):通过处理转换成HTML格式
        targetBlog.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        //更新浏览次数
        blogRepository.updateViews(id);
        return targetBlog;
    }

    @Override //条件分页查询博客
    public Page<Blog> listBlogByCriteria(Pageable pageable, BlogQuery blogQuery) {
        return blogRepository.findAll(new Specification<Blog>() {
            /**
             * 处理动态查询条件
             * @param root  要查询的对象
             * @param query  查询条件的容器
             * @param criteriaBuilder  设置具体某一个条件的表达式
             * @return
             */
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                //定义查询条件的集合
                List<Predicate> predicates = new ArrayList<Predicate>();
                //判断标题查询是否为空
                if(!"".equals(blogQuery.getTitle()) && blogQuery.getTitle()!=null){
                    predicates.add(criteriaBuilder.like(root.<String>get("title"),"%"+blogQuery.getTitle()+"%"));
                }
                //判断分类查询是否为空
                if (blogQuery.getTypeId()!=null){
                    predicates.add(criteriaBuilder.equal(root.<Type>get("type").get("id"),blogQuery.getTypeId()));
                }
                //判断是否推荐是否选中
                if (blogQuery.isRecommend()){
                    predicates.add(criteriaBuilder.equal(root.<Boolean>get("recommend"),blogQuery.isRecommend()));
                }
                //设置查询条件（动态拼接）
                query.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        },pageable);
    }

    @Override//分页查询博客
    public Page<Blog> listAllBlog(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    @Override//分页查询符合对应标签的博客
    public Page<Blog> listBlogByCriteria(Long tagId, Pageable pageable) {
        return blogRepository.findAll(new Specification<Blog>() {
            /**
             * 处理动态查询条件
             * @param root  要查询的对象
             * @param query  查询条件的容器
             * @param criteriaBuilder  设置具体某一个条件的表达式
             * @return
             */
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                //使用关联表查询
                Join join = root.join("tags");
                return criteriaBuilder.equal(join.get("id"),tagId);
            }
        },pageable);
    }

    @Override//查询最新博客
    public List<Blog> listRecommendBlogTop(Integer size) {
        //按博客更新时间进行排序
        Sort sort = Sort.by(Sort.Direction.DESC,"updateTime");
        //创建Pageable对象
        Pageable pageable = PageRequest.of(0,size,sort);
        return blogRepository.findTop(pageable);
    }

    @Override//分组查询博客年份并倒序排列（归档）
    public Map<String, List<Blog>> archivesBlog() {
        //声明Map集合接收数据
        Map<String,List<Blog>> map = new HashMap<>();
        //获取分组查询博客年份并倒序排列的数据集合
        List<String> years = blogRepository.findYearByGroup();
        //循环遍历查询结果
        for (String year : years){
            //根据年份查询对应的博客列表
            map.put(year,blogRepository.findBlogsByYear(year));
        }
        return map;
    }

    @Override//按条件查询博客
    public Page<Blog> listBlogByCriteria(String query, Pageable pageable) {
        return blogRepository.findByQuery(query,pageable);
    }

    @Override//查询博客总记录数
    public Long countBlog() {
        return blogRepository.count();
    }

    @Override//新增博客信息
    public Blog saveBlog(Blog blog) {
        //新增
        if (blog.getId()==null){
            //设置属性值
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
        }else{
            //更新
            blog.setUpdateTime(new Date());
        }
        return blogRepository.save(blog);
    }

    @Override//修改博客信息
    public Blog updateBlogById(Long id, Blog blog) {
        //查询数据库中是否包含该对象
        Blog targetBlog = blogRepository.getOne(id);
        //判断该对象是否存在
        if (targetBlog == null) {
            throw new NotFoundException("该博客不存在！");
        }
        //将blog对象中的值复制到targetBlog对象中,将属性值为空的过滤
        BeanUtils.copyProperties(blog,targetBlog, MyBeanUtils.getNullPropertyNames(blog));

        return blogRepository.save(targetBlog);
    }

    @Override//删除博客信息
    public void deleteBlogById(Long id) {
        blogRepository.deleteById(id);
    }
}
