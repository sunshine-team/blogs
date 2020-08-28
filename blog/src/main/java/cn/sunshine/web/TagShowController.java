package cn.sunshine.web;

/*** @Auther: http://www.bjsxt.com 
 * @Date: 2020/1/28 
 * @Description: cn.sunshine.web
 * @version: 1.0 */

import cn.sunshine.po.Blog;
import cn.sunshine.po.Tag;
import cn.sunshine.po.Type;
import cn.sunshine.service.BlogService;
import cn.sunshine.service.TagService;
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
public class TagShowController {

    @Autowired//注入service层对象
    private TagService tagService;

    @Autowired//注入service层对象
    private BlogService blogService;

    @GetMapping("/tags/{id}")
    public String tags(@PageableDefault(size = 5,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable, @PathVariable Long id, Model model){
        //分页获取所有标签
        List<Tag> tags = tagService.listTagTop(10000);
        //判断博客标签的id（导航栏中设置id为-1）
        if (id == -1){
            id = tags.get(0).getId();
        }
        //分页并获取博客信息
        Page<Blog> page = blogService.listBlogByCriteria(id,pageable);
        //设置模型
        model.addAttribute("tags",tags);
        model.addAttribute("page",page);
        model.addAttribute("activeTagId",id);
        return "tags";
    }

}
