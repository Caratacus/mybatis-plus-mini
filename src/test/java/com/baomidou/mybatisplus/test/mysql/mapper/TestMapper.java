package com.baomidou.mybatisplus.test.mysql.mapper;

import org.apache.ibatis.annotations.Insert;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.test.mysql.entity.Test;

/**
 * <p>
 * 继承 BaseMapper，就自动拥有CRUD方法
 * </p>
 *
 * @author Caratacus hubin
 * @Date 2016-09-25
 */
public interface TestMapper extends BaseMapper<Test> {

    /**
     * 注解插入【测试】
     */
    @Insert("insert into test(id,type) values(#{id},#{type})")
    int insertInjector(Test test);

}
