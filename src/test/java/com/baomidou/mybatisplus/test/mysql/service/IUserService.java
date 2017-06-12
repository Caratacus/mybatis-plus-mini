package com.baomidou.mybatisplus.test.mysql.service;

import com.baomidou.mybatisplus.service.IService;
import com.baomidou.mybatisplus.test.mysql.entity.User;

/**
 * <p>
 * Service层测试
 * </p>
 *
 * @author hubin
 * @date 2017-01-30
 */
public interface IUserService extends IService<User> {

    void testSqlInjector();

}
