package cn.sunshine.web;

import cn.sunshine.po.Blog;
import cn.sunshine.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

/*** @Auther: http://www.bjsxt.com
 * @Date: 2020/1/29 
 * @Description: cn.sunshine.web
 * @version: 1.0 */
@Controller
public class ArchivesShowController {

    @Autowired//注入dao层对象
    private BlogService blogService;

    @GetMapping("/archives")
    public String archives(Model model){
        //调用service层archivesBlog()方法
        Map<String, List<Blog>> archivesMap = blogService.archivesBlog();
        //获取博客总记录数
        Long blogCount = blogService.countBlog();
        //设置模型
        model.addAttribute("archivesMap",archivesMap);
        model.addAttribute("blogCount",blogCount);
        return "archives";
    }
}
