package cn.sunshine.web;

/*** @Auther: http://www.bjsxt.com 
 * @Date: 2020/1/28 
 * @Description: cn.sunshine.web
 * @version: 1.0 */

import cn.sunshine.NotFoundException;
import cn.sunshine.po.Blog;
import cn.sunshine.po.Type;
import cn.sunshine.service.BlogService;
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

import java.util.List;

@Controller
public class TypeShowController {

    @Autowired//注入service层对象
    private TypeService typeService;

    @Autowired//注入service层对象
    private BlogService blogService;

    @GetMapping("/types/{id}")
    public String types(@PageableDefault(size = 5,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable, @PathVariable Long id, Model model){
        //分页获取所有分类
        List<Type> types = typeService.listTypeTop(10000);
        //判断博客分类的id（导航栏中设置id为-1）
        if (id == -1){
            id = types.get(0).getId();
        }
        //创建blogQuery对象
        BlogQuery blogQuery = new BlogQuery();
        blogQuery.setTypeId(id);
        //分页并获取博客信息
        Page<Blog> page = blogService.listBlogByCriteria(pageable,blogQuery);
        //设置模型
        model.addAttribute("types",types);
        model.addAttribute("page",page);
        model.addAttribute("activeTypeId",id);
        return "types";
    }

}
