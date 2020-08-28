package cn.sunshine.dao;

/*** @Auther: http://www.bjsxt.com 
 * @Date: 2020/1/13 
 * @Description: cn.sunshine.dao
 * @version: 1.0 */

import cn.sunshine.po.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * dao层用户接口类
 */
@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    //用户登录(根据用户名和密码查询用户信息)
    public User findByUserNameAndPassword(String username ,String password);
}
