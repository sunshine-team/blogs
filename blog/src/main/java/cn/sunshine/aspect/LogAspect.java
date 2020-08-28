package cn.sunshine.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/*** @Auther: http://www.bjsxt.com
 * @Date: 2020/1/7 
 * @Description: cn.sunshine.aspect
 * @version: 1.0 */

/**
 * 日志记录类
 * @Aspect: 开启面向切面的操作
 * @Component: 开启注解扫描
 */
@Aspect
@Component
public class LogAspect {
    //日志记录
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 定义切面
     */
    @Pointcut("execution(* cn.sunshine.web.*.*(..))")
    public void log(){}

    /**
     * 前置通知
     */
    @Before("log()")
    public void doBefore(JoinPoint joinPoint){
        //获取ServletRequestAttributes对象
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //获取request对象
        HttpServletRequest request = attributes.getRequest();
        //获取url
        String url = request.getRequestURL().toString();
        //获取ip
        String ip = request.getRemoteAddr();
        //获取classMethod
        String classMethod = joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName();
        //获取请求参数
        Object [] args = joinPoint.getArgs();
        //调用内部类有参构造
        RequestLog requestLog = new RequestLog(url,ip,classMethod,args);
        //输出前置通知消息
        logger.info("Request  -----  {}",requestLog);

    }

    /**
     * 后置通知
     */
     @AfterReturning(returning = "result",pointcut = "log()")
     public void doAfterReturn(Object result){
         logger.info("Request : {}",result);
     }

    /**
     * 最终通知
     */
    @After("log()")
    public void doAfter(){

    }

    /**
     * 定义内部类,封装通知信息
     */
    private class RequestLog{
        private String url;//请求url
        private String ip;//访问者ip
        private String classMethod;//调用方法classMethod
        private Object[] args;//请求参数args

        public RequestLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "RequestLog{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }
}
