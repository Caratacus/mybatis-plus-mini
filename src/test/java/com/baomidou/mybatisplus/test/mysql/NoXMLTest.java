package com.baomidou.mybatisplus.test.mysql;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.baomidou.mybatisplus.MybatisSessionFactoryBuilder;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.test.mysql.entity.Test;
import com.baomidou.mybatisplus.test.mysql.mapper.TestMapper;
import com.baomidou.mybatisplus.toolkit.IdWorker;

/**
 * <p>
 * 测试没有XML同样注入CRUD SQL
 * </p>
 *
 * @author Caratacus
 * @date 2016-09-26
 */
public class NoXMLTest {

    public static void main(String[] args) {
        /*
         * 加载配置文件
		 */
        InputStream in = NoXMLTest.class.getClassLoader().getResourceAsStream("mysql-config.xml");
        MybatisSessionFactoryBuilder mf = new MybatisSessionFactoryBuilder();
        SqlSessionFactory sessionFactory = mf.build(in);
        SqlSession sqlSession = sessionFactory.openSession();
        /**
         * 查询是否有结果
         */
        TestMapper testMapper = sqlSession.getMapper(TestMapper.class);
        testMapper.insert(new Test(IdWorker.getId(), "Caratacus"));
        List<Map<String, Object>> list = testMapper.selectMaps(null);
        List<Map<String, Object>> list1 = testMapper.selectMapsPage(RowBounds.DEFAULT, null);
        List<Map<String, Object>> list2 = testMapper.selectMapsPage(new Page<>(1, 5), null);
        System.out.println(list);
        System.out.println(list1);
        System.out.println(list2);
        testMapper.delete(null);

    }

}
