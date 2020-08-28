package cn.sunshine.web.admin;

/*** @Auther: http://www.bjsxt.com 
 * @Date: 2020/1/15 
 * @Description: cn.sunshine.web.admin
 * @version: 1.0 */

import cn.sunshine.po.Tag;
import cn.sunshine.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * tag请求处理层
 */
@Controller
@RequestMapping("/admin")
public class TagController {

    //定义静态常量
    private static final String INPUT = "admin/tags-input";
    private static final String LIST = "admin/tags";
    private static final String REDIRECT_LIST = "redirect:/admin/tags";

    @Autowired//注入service层对象
    private TagService tagService;

    /**
     * @param pageable  //根据前端页面构造好的参数自动封装到pageable对象中
     * @param model
     * @return
     */
    @GetMapping("/tags")
    public String tags(@PageableDefault(size = 5,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable, Model model){
        //调用service层listAllTag方法
        Page<Tag> page =  tagService.listAllTag(pageable);
        //设置模型
        model.addAttribute("page",page);
        return LIST;
    }

    /**
     * 跳转到新增页面
     * @return
     */
    @GetMapping("/tags/input")
    public String input(Model model){
        model.addAttribute("tag",new Tag());
        return INPUT;
    }


    @PostMapping("/tags")
    public String post(@Valid Tag tag, BindingResult result, RedirectAttributes attributes){
        //获取标签名称
        Tag tname =  tagService.getTagByName(tag.getName());
        //判断该标签名称是否存在
        if (tname != null){
            //使用BindingResult对象添加验证结果
            result.rejectValue("name","nameError","该标签已存在！");
        }
        //判断校验结果是否通过
        if (result.hasErrors()){
            return INPUT;
        }
        //调用service层新增方法
        Tag t = tagService.saveTag(tag);
        //判断该对象是否为空
        if(t == null){
            //保存失败
            attributes.addFlashAttribute("message","新增失败");
        }else{
            //保存成功
            attributes.addFlashAttribute("message","新增成功");
        }
        return REDIRECT_LIST;
    }

    /**
     * 跳转到修改页面
     * @param id   标签编号
     * @param model
     * @return
     */
    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        //获取要修改的tag对象
        Tag tag = tagService.getTagById(id);
        //设置模型
        model.addAttribute("tag",tag);
        return INPUT;
    }

    /**
     * 标签修改
     * @param tag
     * @param result    接收校验后的结果
     * @param id
     * @param attributes    向页面输出信息
     * @return
     */
    @PostMapping("/tags/{id}")
    public String editPost(@Valid Tag tag, BindingResult result, @PathVariable Long id, RedirectAttributes attributes){
        //获取标签名称
        Tag tname =  tagService.getTagByName(tag.getName());
        //判断该标签名称是否存在
        if (tname != null){
            //使用BindingResult对象添加验证结果
            result.rejectValue("name","nameError","该标签已存在！");
        }
        //判断校验结果是否通过
        if (result.hasErrors()){
            return INPUT;
        }
        //调用service层新增方法
        Tag t = tagService.updateTagById(id,tag);
        //判断该对象是否为空
        if(t == null){
            //保存失败
            attributes.addFlashAttribute("message","更新失败");
        }else{
            //保存成功
            attributes.addFlashAttribute("message","更新成功");
        }
        return REDIRECT_LIST;
    }

    /**
     * 标签删除
     * @param id
     * @param attributes
     * @return
     */
    @GetMapping("tags/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        //获取要删除的标签对象
        Tag tag = tagService.getTagById(id);
        //判断该对象是否存在
        if(tag != null){
            //该标签存在
            tagService.deleteTagById(id);
            attributes.addFlashAttribute("message","删除成功");
        }else{
            //该标签不存在
            attributes.addFlashAttribute("message","删除失败");
        }
        return REDIRECT_LIST;
    }

}
