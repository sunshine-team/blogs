package cn.sunshine.vo;

/*** @Auther: http://www.bjsxt.com 
 * @Date: 2020/1/15 
 * @Description: cn.sunshine.vo
 * @version: 1.0 */

/**
 * 博客查询的包装类
 */
public class BlogQuery {
    private String title;//标题
    private Long typeId;//分类编号
    private boolean recommend;//是否推荐

    public BlogQuery() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }
}
