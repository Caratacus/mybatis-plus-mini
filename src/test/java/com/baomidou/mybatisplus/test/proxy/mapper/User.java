package com.baomidou.mybatisplus.test.proxy.mapper;

/**
 * <p>
 * mybatis 执行原理测试对象
 * </p>
 *
 * @author hubin
 * @Date 2016-07-06
 */
public class User {

    private Long id;

    private String name;

    private int age;


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


    public int getAge() {
        return age;
    }


    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "id=[" + id + "], name=[" + name + "], age=[" + age + "]";
    }

}
