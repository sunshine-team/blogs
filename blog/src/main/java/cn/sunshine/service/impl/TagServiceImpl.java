package cn.sunshine.service.impl;

/*** @Auther: http://www.bjsxt.com 
 * @Date: 2020/1/15 
 * @Description: cn.sunshine.service.impl
 * @version: 1.0 */

import cn.sunshine.NotFoundException;
import cn.sunshine.po.Tag;
import cn.sunshine.dao.TagRepository;
import cn.sunshine.service.TagService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * service层博客标签接口实现类
 */
@Service
@Transactional
public class TagServiceImpl implements TagService {

    @Autowired//注入dao层对象
    private TagRepository tagRepository;

    @Override//新增博客标签
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override//按编号查询博客标签
    public Tag getTagById(Long id) {
        return tagRepository.getOne(id);
    }

    @Override//按名称查询博客标签
    public Tag getTagByName(String name) {
        return tagRepository.findTagByName(name);
    }

    @Override//分页查询标签
    public Page<Tag> listAllTag(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Override//查询所有标签
    public List<Tag> listAllTag() {
        return tagRepository.findAll();
    }

    @Override//查询所有标签ID(例：ids:1,2,3)
    public List<Tag> listAllTag(String ids) {
        return tagRepository.findAllById(convertToList(ids));
    }

    //将字符串转换成数组
    private List<Long> convertToList(String ids) {
        List<Long> list = new ArrayList<>();
        if (!"".equals(ids) && ids != null) {
            String[] idArray = ids.split(",");
            for (int i=0; i < idArray.length;i++) {
                list.add(new Long(idArray[i]));
            }
        }
        return list;
    }

    @Override//按博客标签数量大小查询标签
    public List<Tag> listTagTop(Integer size) {
        //按博客所属标签列表大小进行排序
        Sort sort = Sort.by(Sort.Direction.DESC,"blogs.size");
        //创建Pageable对象
        Pageable pageable = PageRequest.of(0,size,sort);
        return tagRepository.findTop(pageable);
    }

    @Override//修改博客标签
    public Tag updateTagById(Long id, Tag tag) {
        //查询数据库中是否包含该对象
        Tag targetTag = tagRepository.getOne(id);
        //判断该标签是否存在
        if (targetTag == null){
            throw new NotFoundException("该博客标签不存在");
        }
        //将tag对象中的值复制到targetTag对象中
        BeanUtils.copyProperties(tag,targetTag);
        return tagRepository.save(targetTag);
    }

    @Override//删除博客标签
    public void deleteTagById(Long id) {
        tagRepository.deleteById(id);
    }
}
