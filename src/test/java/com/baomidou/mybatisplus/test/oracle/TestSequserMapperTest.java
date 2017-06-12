package com.baomidou.mybatisplus.test.oracle;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.baomidou.mybatisplus.MybatisSessionFactoryBuilder;
import com.baomidou.mybatisplus.entity.GlobalConfiguration;
import com.baomidou.mybatisplus.test.oracle.entity.TestSequser;


/**
 * <p>
 * oracle sequence 测试类
 * </p>
 *
 * @author zashitou
 * @Date 2017-04-20
 */
public class TestSequserMapperTest {


    /**
     * Test Oracle Sequence
     */
    public static void main(String[] args) {

        //加载配置文件
        InputStream in = TestSequserMapperTest.class.getClassLoader().getResourceAsStream("oracle-config.xml");

        MybatisSessionFactoryBuilder mf = new MybatisSessionFactoryBuilder();

        /** 设置数据库类型为 oracle */
        GlobalConfiguration gc = new GlobalConfiguration();
        gc.setDbType("oracle");
        gc.setDbColumnUnderline(true);
        mf.setGlobalConfig(gc);
        SqlSessionFactory sessionFactory = mf.build(in);
        SqlSession session = sessionFactory.openSession();
        TestSequserMapper testSequserMapper = session.getMapper(TestSequserMapper.class);
        /**
         * 插入
         */
        TestSequser one = new TestSequser("abc", 18, 1);
        int rlt = testSequserMapper.insert(one);
        System.err.println("\n one.id-------:" + one.getId());
        sleep();

        /**
         * 批量插入
         */
        List<TestSequser> ul = new ArrayList<>();
        ul.add(new TestSequser("abc2", 18, 2));
        ul.add(new TestSequser("abc3", 18, 3));
        ul.add(new TestSequser("abc4", 18, 4));
        ul.add(new TestSequser("abc5", 18, 5));
        ul.add(new TestSequser("abc6", 18, 6));
        for (TestSequser u : ul) {
            rlt = testSequserMapper.insert(u);
        }
        for (TestSequser u : ul) {
            System.err.println("\n one.id-------:" + u.getId());
        }

        /**
         * 提交
         */
        session.commit();
    }


    /*
     * 慢点打印
     */
    private static void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
