package com.baomidou.mybatisplus.test.mysql;

import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.session.Configuration;

import com.baomidou.mybatisplus.entity.TableInfo;
import com.baomidou.mybatisplus.sqlInjector.AutoSqlInjector;

/**
 * <p>
 * 测试自定义注入 SQL
 * </p>
 *
 * @author hubin
 * @Date 2016-07-23
 */
public class MySqlInjector extends AutoSqlInjector {

    @Override
    public void inject(Configuration configuration, MapperBuilderAssistant builderAssistant, Class<?> mapperClass,
                       Class<?> modelClass, TableInfo table) {
        /* 添加一个自定义方法 */
        deleteAllUser(mapperClass, modelClass, table);
        // 测试 com.baomidou.mybatisplus.test.mysql.MetaObjectHandlerTest
        deleteLogicById(mapperClass, modelClass, table);
    }

    public void deleteAllUser(Class<?> mapperClass, Class<?> modelClass, TableInfo table) {

		/* 执行 SQL ，动态 SQL 参考类 SqlMethod */
        String sql = "delete from " + table.getTableName();

		/* mapper 接口方法名一致 */
        String method = "deleteAll";
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        this.addDeleteMappedStatement(mapperClass, method, sqlSource);
    }

    public void deleteLogicById(Class<?> mapperClass, Class<?> modelClass, TableInfo table) {

		/* 执行 SQL ，动态 SQL 参考类 SqlMethod */
        String sql = String.format("UPDATE %s SET test_type=-1 WHERE test_id=#{id}", table.getTableName());

		/* mapper 接口方法名一致 */
        String method = "deleteLogicById";
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);

        // 注意！！ 这里是更新、删除、插入、调用方法注入不一样！！
        this.addUpdateMappedStatement(mapperClass, modelClass, method, sqlSource);
    }
}
