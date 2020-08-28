package cn.sunshine.interceptor;

/*** @Auther: http://www.bjsxt.com 
 * @Date: 2020/1/13 
 * @Description: cn.sunshine.interceptor
 * @version: 1.0 */

import cn.sunshine.po.User;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限校验
 */
public class PrivilegeInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取存在session中的user对象
        User user = (User) request.getSession().getAttribute("user");
        //判断该对象是否为空
        if (user == null){
            response.sendRedirect("/admin");
            return false;
        }
        return true;
    }
}
