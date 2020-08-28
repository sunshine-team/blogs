package cn.sunshine;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/*** @Auther: http://www.bjsxt.com
 * @Date: 2020/1/7 
 * @Description: cn.sunshine
 * @version: 1.0 */

/**
 * @ResponseStatus : 响应状态码 (NOT_FOUND：找不到资源)
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException{

    public NotFoundException() {}

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
