package cn.sunshine.handler;

/*** @Auther: http://www.bjsxt.com 
 * @Date: 2020/1/7 
 * @Description: cn.sunshine.handler
 * @version: 1.0 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 统一异常处理类
 * @ControllerAdvice:对标注@Controller注解的控制器进行拦截
 */
@ControllerAdvice
public class ControllerExeceptionHandler {

    //日志记录
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 异常处理
     * @ExceptionHandler    //标识此方法用于异常处理
     * @param request   //访问的哪个路径出现异常，可以通过request对象获取
     * @param e     //异常类型
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(HttpServletRequest request,Exception e) throws Exception{
        //记录请求的URL,异常信息
        logger.error("Request URL : {},Exception : {}",request.getRequestURL(),e);
        //判断该类是否标注@ResponseStatus注解，如果没有标注，就抛出异常交给springboot处理
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null){
            throw e;
        }
        //创建ModelAndView对象
        ModelAndView mav = new ModelAndView();
        //获取请求路径
        mav.addObject("url",request.getRequestURL());
        //获取异常信息
        mav.addObject("exception",e);
        //设置视图名称
        mav.setViewName("error/error");
        return mav;
    }
}
