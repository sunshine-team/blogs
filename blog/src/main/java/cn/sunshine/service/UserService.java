package cn.sunshine.service;

/*** @Auther: http://www.bjsxt.com 
 * @Date: 2020/1/13 
 * @Description: cn.sunshine.service
 * @version: 1.0 */

import cn.sunshine.po.User;

/**
 * service层用户接口类
 */
public interface UserService {
    //用户登录
    public User checkUser(String username , String password);
}
