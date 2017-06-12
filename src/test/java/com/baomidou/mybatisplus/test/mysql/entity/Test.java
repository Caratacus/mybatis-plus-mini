package com.baomidou.mybatisplus.test.mysql.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;

/**
 * <p>
 * 测试没有XML同样注入CRUD SQL 实体
 * </p>
 *
 * @author Caratacu
 * @Date 2016-09-25
 */
// 表名为 test 可以不需要注解，特殊如 @TableName("tb_test")
public class Test extends Model<Test> {

    // 静态属性会自动忽略
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    // 默认会找 id 为主键，特殊命名需要注解 @TableId
    private Long id;

    private String type;

    @TableField("create_time")
    private Date createTime;

    public Test() {

    }

    public Test(Long id, String type) {
        this.id = id;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    protected Serializable pkVal() {
        return id;
    }

    public String toString() {
        return "{id=" + id + ",type=" + type + "}";
    }

}
