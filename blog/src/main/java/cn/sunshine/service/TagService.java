package cn.sunshine.service;

/*** @Auther: http://www.bjsxt.com 
 * @Date: 2020/1/15 
 * @Description: cn.sunshine.service
 * @version: 1.0 */

import cn.sunshine.po.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * service层博客标签接口类
 */
public interface TagService {
    //新增博客标签
    public Tag saveTag(Tag type);
    //按编号查询博客标签
    public Tag getTagById(Long id);
    //按名称查询博客标签
    public Tag getTagByName(String name);
    //分页查询标签
    public Page<Tag> listAllTag(Pageable pageable);
    //查询所有标签
    public List<Tag> listAllTag();
    //按博客标签数量大小查询标签
    public List<Tag> listTagTop(Integer size);
    //查询所有标签ID
    public List<Tag> listAllTag(String ids);
    //修改博客标签
    public Tag updateTagById(Long id,Tag type);
    //删除博客标签
    public void deleteTagById(Long id);
}
