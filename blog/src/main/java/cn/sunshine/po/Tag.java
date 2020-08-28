package cn.sunshine.po;

/*** @Auther: http://www.bjsxt.com 
 * @Date: 2020/1/13 
 * @Description: cn.sunshine.bean
 * @version: 1.0 */

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * 博客标签实体类
 */
@Entity
@Table(name="t_tag")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;//标签编号

    @NotBlank(message="标签名称不能为空")
    private String name;//标签名称

    @ManyToMany(mappedBy = "tags")
    private List<Blog> blogs = new ArrayList<Blog>();//博客列表集合

    public Tag() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
