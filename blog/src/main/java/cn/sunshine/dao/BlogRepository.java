package cn.sunshine.dao;

import cn.sunshine.po.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/*** @Auther: http://www.bjsxt.com
 * @Date: 2020/1/15 
 * @Description: cn.sunshine.dao
 * @version: 1.0 */

/**
 * dao层博客接口类
 */
public interface BlogRepository extends JpaRepository<Blog,Long>, JpaSpecificationExecutor<Blog> {
    //自定义查询（按博客更新时间）
    @Query("select b from Blog b where b.recommend=true")
    public List<Blog> findTop(Pageable pageable);

    //按条件查询博客
    @Query("select b from Blog b where b.title like ?1 or b.content like ?1")
    public Page<Blog> findByQuery(String query,Pageable pageable);

    //按照博客Id修改浏览量
    @Modifying
    @Query("update Blog b set b.views = b.views+1 where b.id = ?1")
    public Integer updateViews(Long id);

    //分组查询博客年份并倒序排列（归档）
    @Query("select function('DATE_FORMAT',b.updateTime,'%Y') as year from Blog b group by function('DATE_FORMAT',b.updateTime,'%Y') order by year DESC")
    public List<String> findYearByGroup();

    //根据博客年份查询博客信息
    @Query("select b from Blog b where function('DATE_FORMAT',b.updateTime,'%Y') = ?1")
    public List<Blog> findBlogsByYear(String year);
}
