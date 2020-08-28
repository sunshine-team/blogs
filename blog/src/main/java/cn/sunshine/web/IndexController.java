package cn.sunshine.web;

import cn.sunshine.po.Blog;
import cn.sunshine.po.Tag;
import cn.sunshine.po.Type;
import cn.sunshine.service.BlogService;
import cn.sunshine.service.TagService;
import cn.sunshine.service.TypeService;
import cn.sunshine.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/*** @Auther: http://www.bjsxt.com
 * @Date: 2020/1/21 
 * @Description: cn.sunshine.web
 * @version: 1.0 */
@Controller
public class IndexController {

    @Autowired//注入service对象
    private BlogService blogService;

    @Autowired//注入service对象
    private TypeService typeService;

    @Autowired//注入service对象
    private TagService tagService;

    /**
     *  分页查询博客信息（分类，标签）
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping("/")
    public String index(@PageableDefault(size = 5,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable, Model model){
        //获取所有博客信息
        Page<Blog> page = blogService.listAllBlog(pageable);
        //获取博客分类（指定显示博客所属分类数量最大的6个分类）
        List<Type> types = typeService.listTypeTop(6);
        //获取博客标签（指定显示博客所属标签数量最大的10个标签）
        List<Tag> tags = tagService.listTagTop(10);
        //获取最新博客信息（指定显示最新博客数量为8条）
        List<Blog> blogs = blogService.listRecommendBlogTop(8);
        //设置模型
        model.addAttribute("page",page);
        model.addAttribute("types",types);
        model.addAttribute("tags",tags);
        model.addAttribute("recommendBlogs",blogs);
        return "index";
    }

    /**
     * 按条件搜索
     * @param pageable
     * @param model
     * @return
     */
    @PostMapping("/search")
    public String search(@PageableDefault(size = 5,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable, @RequestParam String query, Model model){
        //按条件获取博客信息
        Page<Blog> page = blogService.listBlogByCriteria("%"+query+"%",pageable);
        //设置模型
        model.addAttribute("page",page);
        model.addAttribute("query",query);
        return "search";
    }


    /**
     * 获取博客ID跳转到博客详情页面
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model){
        //根据博客ID获取博客详情
        Blog blog = blogService.getAndConvert(id);
        //设置模型
        model.addAttribute("blog",blog);
        return "blog";
    }

    @GetMapping("/footer/newBlogs")
    public String newBlogs(Model model){
        //获取最新博客信息（指定显示最新博客数量为3条）
        List<Blog> blogs = blogService.listRecommendBlogTop(3);
        //设置模型
        model.addAttribute("newBlogs",blogs);
        return "_fragments :: newBlogList";
    }
}
