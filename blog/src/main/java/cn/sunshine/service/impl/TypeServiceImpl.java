package cn.sunshine.service.impl;

import cn.sunshine.NotFoundException;
import cn.sunshine.po.Type;
import cn.sunshine.dao.TypeRepository;
import cn.sunshine.service.TypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/*** @Auther: http://www.bjsxt.com
 * @Date: 2020/1/13 
 * @Description: cn.sunshine.service.impl
 * @version: 1.0 */

/**
 * service层博客分类接口实现类
 */
@Transactional
@Service
public class TypeServiceImpl implements TypeService {

    @Autowired//注入service层对象
    private TypeRepository typeRepository;

    @Override//新增博客分类
    public Type saveType(Type type) {
        return typeRepository.save(type);
    }

    @Override//按编号查询博客分类
    public Type getTypeById(Long id) {
        return typeRepository.getOne(id);
    }

    @Override//按名称查询博客分类
    public Type getTypeByName(String name) {
        return typeRepository.findTypeByName(name);
    }

    @Override//分页查询分类
    public Page<Type> listAllType(Pageable pageable) {
        return typeRepository.findAll(pageable);
    }

    @Override//查询所有分类
    public List<Type> listAllType() {
        return typeRepository.findAll();
    }

    @Override//按博客分类数量查询分类
    public List<Type> listTypeTop(Integer size) {
        //按博客所属分类列表大小进行排序
        Sort sort = Sort.by(Sort.Direction.DESC,"blogs.size");
        //创建Pageable对象
        Pageable pageable = PageRequest.of(0,size,sort);
        return typeRepository.findTop(pageable);
    }

    @Override//修改博客分类
    public Type updateTypeById(Long id, Type type) {
        //查询数据库中是否包含该对象
        Type targetType = typeRepository.getOne(id);
        //判断该分类是否存在
        if (targetType==null){
            throw new NotFoundException("该博客分类不存在！");
        }
        //将type对象中的值复制到targetType对象中
        BeanUtils.copyProperties(type,targetType);
        return typeRepository.save(targetType);
    }

    @Override//删除博客分类
    public void deleteTypeById(Long id) {
        typeRepository.deleteById(id);
    }
}
