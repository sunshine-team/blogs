package cn.sunshine.web.admin;

/*** @Auther: http://www.bjsxt.com 
 * @Date: 2020/1/13 
 * @Description: cn.sunshine.web.admin
 * @version: 1.0 */

import cn.sunshine.po.Type;
import cn.sunshine.service.TypeService;
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
 * type请求处理层
 */
@Controller
@RequestMapping("/admin")
public class TypeController {

    //定义静态常量
    private static final String INPUT = "admin/types-input";
    private static final String LIST = "admin/types";
    private static final String REDIRECT_LIST = "redirect:/admin/types";

    @Autowired//注入service层对象
    private TypeService typeService;

    /**
     *
     * @param pageable   //根据前端页面构造好的参数自动封装到pageable对象中
     * @param model
     * @return
     */
    @GetMapping("/types")
    public String types(@PageableDefault(size = 5,sort = {"id"},direction = Sort.Direction.DESC)
                                    Pageable pageable, Model model){
        //调用service层listAllType方法
        Page<Type> page = typeService.listAllType(pageable);
        //设置模型
        model.addAttribute("page",page);
        return LIST;
    }

    /**
     * 跳转到新增页面
     * @return
     */
    @GetMapping("/types/input")
    public String input(Model model){
        model.addAttribute("type",new Type());
        return INPUT;
    }

    /**
     * 分类新增
     * RedirectAttributes   向页面输出信息
     * BindingResult  接收校验后的结果
     * @return
     */
    @PostMapping("/types")
    public String post(@Valid Type type, BindingResult result , RedirectAttributes attributes){
        //获取分类名称
        Type tname = typeService.getTypeByName(type.getName());
        //判断该分类名称是否存在
        if(tname!=null){
            //使用BindingResult对象添加验证结果
            result.rejectValue("name","nameError","该分类已存在！");
        }
        //判断校验结果是否通过
        if (result.hasErrors()){
            return INPUT;
        }
        //调用service层新增方法
        Type t = typeService.saveType(type);
        //判断该对象是否为空
        if (t == null){
            //保存失败
            attributes.addFlashAttribute("message","新增失败");
        } else {
            //保存成功
            attributes.addFlashAttribute("message","新增成功");
        }
        return REDIRECT_LIST;
    }

    /**
     * 跳转到修改页面
     * @param id  分类编号
     * @param model
     * @return
     */
    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        //获取要修改的type对象
        Type type = typeService.getTypeById(id);
        //设置模型
        model.addAttribute("type",type);
        return INPUT;
    }


    /**
     *
     * @param type
     * @param result    接收校验后的结果
     * @param id
     * @param attributes    向页面输出信息
     * @return
     */
    @PostMapping("/types/{id}")
    public String editPost(@Valid Type type, BindingResult result ,@PathVariable Long id, RedirectAttributes attributes){
        //获取分类名称
        Type tname = typeService.getTypeByName(type.getName());
        //判断分类名称是否存在
        if(tname!=null){
            //使用BindingResult对象添加验证结果
            result.rejectValue("name","nameError","该分类已存在！");
        }
        //判断校验结果是否通过
        if (result.hasErrors()){
            return INPUT;
        }
        //调用service层修改方法
        Type t = typeService.updateTypeById(id,type);
        //判断该对象是否为空
        if (t == null){
            //保存失败
            attributes.addFlashAttribute("message","更新失败");
        } else {
            //保存成功
            attributes.addFlashAttribute("message","更新成功");
        }
        return REDIRECT_LIST;
    }

    /**
     * 分类删除
     * @param id
     * @param attributes
     * @return
     */
    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        //获取要删除的分类对象
        Type type = typeService.getTypeById(id);
        //判断该对象是否存在
        if (type != null){
            //该分类存在
            typeService.deleteTypeById(id);
            attributes.addFlashAttribute("message","删除成功");
        }else{
            //该分类不存在
            attributes.addFlashAttribute("message","删除失败");
        }
        return REDIRECT_LIST;
    }
}
