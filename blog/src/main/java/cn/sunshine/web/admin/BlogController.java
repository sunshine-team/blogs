package cn.sunshine.web.admin;

/*** @Auther: http://www.bjsxt.com 
 * @Date: 2020/1/13 
 * @Description: cn.sunshine.web.admin
 * @version: 1.0 */

import cn.sunshine.po.Blog;
import cn.sunshine.po.Tag;
import cn.sunshine.po.Type;
import cn.sunshine.po.User;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 *blog请求处理层
 */

@Controller
@RequestMapping("/admin")
public class BlogController {

    //定义静态常量
    private static final String INPUT = "admin/blogs-input";
    private static final String LIST = "admin/blogs";
    private static final String REDIRECT_LIST = "redirect:/admin/blogs";

    @Autowired//注入service对象
    private BlogService blogService;

    @Autowired//注入service对象
    private TypeService typeService;

    @Autowired//注入service对象
    private TagService tagService;


    /**
     * 跳转到博客列表页面
     * @param pageable
     * @param blogQuery
     * @param model
     * @return
     */
    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 5,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blogQuery, Model model){
        //获取所有博客分类
        List<Type> types = typeService.listAllType();
        //调用service层listBlogByCriteria方法
        Page<Blog> page = blogService.listBlogByCriteria(pageable,blogQuery);
        //设置模型
        model.addAttribute("types",types);
        model.addAttribute("page",page);
        return LIST;
    }

    /**
     * 按条件查询博客信息
     * @param pageable
     * @param blogQuery
     * @param model
     * @return
     */
    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 5,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blogQuery, Model model){
        //调用service层listBlogByCriteria方法
        Page<Blog> page = blogService.listBlogByCriteria(pageable,blogQuery);
        //设置模型
        model.addAttribute("page",page);
        //刷新admin/blogs页面中的blogList片段
        return "admin/blogs :: blogList";
    }

    /**
     * 新增或修改时查询博客类型和标签
     * @param model
     */
    private void setTypeAndTag(Model model){
        //获取所有博客分类
        List<Type> types = typeService.listAllType();
        //获取所有博客标签
        List<Tag> tags = tagService.listAllTag();
        //设置模型
        model.addAttribute("types",types);
        model.addAttribute("tags",tags);
    }

    /**
     * 跳转到新增页面
     * @param model
     * @return
     */
    @GetMapping("/blogs/input")
    public String input(Model model){
        //调用本类的方法
        setTypeAndTag(model);
        model.addAttribute("blog",new Blog());
        return INPUT;
    }

    /**
     * 新增+修改
     * @param blog
     * @param attributes
     * @param session
     * @return
     */
    @PostMapping("/blogs")
    public String post(Blog blog, RedirectAttributes attributes, HttpSession session){
        //设置当前登录的用户对象
        blog.setUser((User)session.getAttribute("user"));
        //设置博客类型（获取页面中type.id,自动封装到blog对象中）
        blog.setType(typeService.getTypeById(blog.getType().getId()));
        //设置被选中的博客标签编号
        blog.setTags(tagService.listAllTag(blog.getTagIds()));
        //调用service层新增方法
        Blog b ;
        //判断blog对象在数据库中是否存在id
        if (blog.getId()==null){
            //新增
            b = blogService.saveBlog(blog);
        }else{
            //修改
            b = blogService.updateBlogById(blog.getId(),blog);
        }

        //判断该对象是否为空
        if (b == null){
            attributes.addFlashAttribute("message","操作失败");
        }else{
            attributes.addFlashAttribute("message","操作成功");
        }
        return REDIRECT_LIST;
    }

    /**
     * 跳转到修改页面
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/blogs/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        //调用本类的方法
        setTypeAndTag(model);
        //获取要修改的blog对象
        Blog blog = blogService.getBlogById(id);
        //初始化tagIds
        blog.init();
        //设置模型
        model.addAttribute("blog",blog);
        return INPUT;
    }

    /**
     * 删除博客
     * @param id
     * @param attributes
     * @return
     */
    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        //获取要删除的博客对象
        Blog blog = blogService.getBlogById(id);
        //判断该对象是否存在
        if(blog != null){
            //该博客存在
            blogService.deleteBlogById(id);
            attributes.addFlashAttribute("message","删除成功");
        }else{
            //该博客不存在
            attributes.addFlashAttribute("message","删除失败");
        }
        return REDIRECT_LIST;
    }
}
