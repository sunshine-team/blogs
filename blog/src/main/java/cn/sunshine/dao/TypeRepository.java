package cn.sunshine.dao;

/*** @Auther: http://www.bjsxt.com 
 * @Date: 2020/1/13 
 * @Description: cn.sunshine.dao
 * @version: 1.0 */

import cn.sunshine.po.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * dao层博客分类接口类
 */
public interface TypeRepository extends JpaRepository<Type,Long> {
    //按名称查询博客分类
    public Type findTypeByName(String name);

    //自定义查询（按博客所属分类的数量）
    @Query("select t from Type t")
    public List<Type> findTop(Pageable pageable);

}
