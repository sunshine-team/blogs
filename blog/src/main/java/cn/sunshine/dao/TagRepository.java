package cn.sunshine.dao;

/*** @Auther: http://www.bjsxt.com 
 * @Date: 2020/1/15 
 * @Description: cn.sunshine.dao
 * @version: 1.0 */

import cn.sunshine.po.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * dao层博客标签接口类
 */
public interface TagRepository extends JpaRepository<Tag,Long> {
    //按名称查询博客标签
    public Tag findTagByName(String name);
    //自定义查询（按博客所属标签的数量）
    @Query("select t from Tag t")
    public List<Tag> findTop(Pageable pageable);
}
