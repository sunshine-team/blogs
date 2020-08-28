package cn.sunshine.service;

/*** @Auther: http://www.bjsxt.com 
 * @Date: 2020/1/13 
 * @Description: cn.sunshine.service
 * @version: 1.0 */

import cn.sunshine.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * service层博客分类接口类
 */
public interface TypeService {
    //新增博客分类
    public Type saveType(Type type);
    //按编号查询博客分类
    public Type getTypeById(Long id);
    //按名称查询博客分类
    public Type getTypeByName(String name);
    //分页查询分类
    public Page<Type> listAllType(Pageable pageable);
    //查询所有分类
    public List<Type> listAllType();
    //按博客分类数量大小查询分类
    public List<Type> listTypeTop(Integer size);
    //修改博客分类
    public Type updateTypeById(Long id,Type type);
    //删除博客分类
    public void deleteTypeById(Long id);
}
