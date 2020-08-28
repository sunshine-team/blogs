package cn.sunshine.service.impl;

import cn.sunshine.po.User;
import cn.sunshine.dao.UserRepository;
import cn.sunshine.service.UserService;
import cn.sunshine.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*** @Auther: http://www.bjsxt.com
 * @Date: 2020/1/13 
 * @Description: cn.sunshine.service.impl
 * @version: 1.0 */

/**
 * service层用户接口实现类
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired//注入dao层对象
    private UserRepository userRepository;

    @Override//用户登录
    public User checkUser(String username, String password) {
        //调用dao层findByUserNameAndPassword方法
        User user = userRepository.findByUserNameAndPassword(username, MD5Utils.code(password));
        return user;
    }
}
