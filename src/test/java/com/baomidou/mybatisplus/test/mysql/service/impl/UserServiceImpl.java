package com.baomidou.mybatisplus.test.mysql.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.test.mysql.entity.User;
import com.baomidou.mybatisplus.test.mysql.mapper.UserMapper;
import com.baomidou.mybatisplus.test.mysql.service.IUserService;
import com.baomidou.mybatisplus.toolkit.IdWorker;

/**
 * <p>
 * Service层测试
 * </p>
 *
 * @author hubin
 * @date 2017-01-30
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    // 注入测试
    public void testSqlInjector() {
        Long id = IdWorker.getId();
        int rlt = baseMapper.insert(new User(id, "abc", 18, 1));
        System.err.println("插入ID：" + id + ", 执行结果：" + rlt);
        rlt = baseMapper.deleteLogicById(id);
        System.err.println("测试注入执行结果：" + rlt);
        System.err.println("逻辑修改：" + baseMapper.selectById(id).toString());
    }

}
