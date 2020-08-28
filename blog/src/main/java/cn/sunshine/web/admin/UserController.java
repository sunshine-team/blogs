package cn.sunshine.web.admin;

import cn.sunshine.po.User;
import cn.sunshine.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/*** @Auther: http://www.bjsxt.com
 * @Date: 2020/1/13 
 * @Description: cn.sunshine.web.admin
 * @version: 1.0 */

/**
 * user请求处理层
 */
@Controller
@RequestMapping("/admin")
public class UserController {

    @Autowired//注入service对象
    private UserService userService;

    /**
     * 返回登录页面
     * @return
     */
    @GetMapping
    public String toLoginPage(){
        return "admin/login";
    }

    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/login")
    public String login(@RequestParam String username ,
                        @RequestParam String password ,
                        HttpSession session ,
                        RedirectAttributes attributes){
        //调用service层checkUser方法
        User user = userService.checkUser(username,password);
        //判断用户名和密码是否正确
        if (user != null){
            //设置用户密码为空，提高用户安全性
            user.setPassword(null);
            //将登录的用户对象保存到session中
            session.setAttribute("user",user);
            //跳转到主页面
            return "admin/index";
        }else{
            attributes.addFlashAttribute("message","用户名或密码错误！");
            return "redirect:/admin";
        }
    }

    /**
     * 注销
     * @return
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/admin";
    }
}
