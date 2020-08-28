package cn.sunshine.interceptor;

/*** @Auther: http://www.bjsxt.com 
 * @Date: 2020/1/13 
 * @Description: cn.sunshine.interceptor
 * @version: 1.0 */

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *  配置类
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /* *
     * 定义拦截器
     * addPathPatterns():定义拦截器的请求
     * excludePathPatterns():定义不要拦截的请求（登录页面不拦截，登录跳转的controller也不能拦截）
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new PrivilegeInterceptor()).addPathPatterns("/admin/**").excludePathPatterns("/admin","/admin/login");
    }
}
