package cn.sunshine.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/*** @Auther: http://www.bjsxt.com
 * @Date: 2020/1/29 
 * @Description: cn.sunshine.web
 * @version: 1.0 */
@Controller
public class AboutShowController {
    /**
     * 跳转到about页面
     * @return
     */
    @GetMapping("/about")
    public String about(){
        return "about";
    }
}
