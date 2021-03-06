package com.baomidou.mybatisplus.plugins.pagination;

import java.util.Objects;

import org.apache.ibatis.session.RowBounds;

import com.baomidou.mybatisplus.enums.DBType;
import com.baomidou.mybatisplus.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.plugins.pagination.dialects.DB2Dialect;
import com.baomidou.mybatisplus.plugins.pagination.dialects.H2Dialect;
import com.baomidou.mybatisplus.plugins.pagination.dialects.HSQLDialect;
import com.baomidou.mybatisplus.plugins.pagination.dialects.MySqlDialect;
import com.baomidou.mybatisplus.plugins.pagination.dialects.OracleDialect;
import com.baomidou.mybatisplus.plugins.pagination.dialects.PostgreDialect;
import com.baomidou.mybatisplus.plugins.pagination.dialects.SQLServer2005Dialect;
import com.baomidou.mybatisplus.plugins.pagination.dialects.SQLServerDialect;
import com.baomidou.mybatisplus.plugins.pagination.dialects.SQLiteDialect;
import com.baomidou.mybatisplus.toolkit.StringUtils;

/**
 * <p>
 * 分页方言工厂类
 * </p>
 *
 * @author hubin
 * @Date 2016-01-23
 */
public class DialectFactory {

    /**
     * <p>
     * 生成翻页执行 SQL
     * </p>
     *
     * @param page         翻页对象
     * @param buildSql     执行 SQL
     * @param dbType       数据库类型
     * @param dialectClazz 自定义方言实现类
     * @return
     * @throws Exception
     */
    public static String buildPaginationSql(Pagination page, String buildSql, DBType dbType, String dialectClazz)
            throws Exception {
        // fix #172, 196
        return getiDialect(dbType, dialectClazz).buildPaginationSql(buildSql, page.getOffsetCurrent(), page.getSize());
    }

    /**
     * Physical Pagination Interceptor for all the queries with parameter
     * {@link org.apache.ibatis.session.RowBounds}
     *
     * @param rowBounds
     * @param buildSql
     * @param dbType
     * @param dialectClazz
     * @return
     * @throws Exception
     */
    public static String buildPaginationSql(RowBounds rowBounds, String buildSql, DBType dbType, String dialectClazz)
            throws Exception {
        // fix #196
        return getiDialect(dbType, dialectClazz).buildPaginationSql(buildSql, rowBounds.getOffset(), rowBounds.getLimit());
    }

    /**
     * <p>
     * 获取数据库方言
     * </p>
     *
     * @param dbType       数据库类型
     * @param dialectClazz 自定义方言实现类
     * @return
     * @throws Exception
     */
    private static IDialect getiDialect(DBType dbType, String dialectClazz) throws Exception {
        IDialect dialect = null;
        if (Objects.nonNull(dbType)) {
            dialect = getDialectByDbtype(dbType);
        } else {
            if (StringUtils.isNotEmpty(dialectClazz)) {
                try {
                    Class<?> clazz = Class.forName(dialectClazz);
                    if (IDialect.class.isAssignableFrom(clazz)) {
                        dialect = (IDialect) clazz.newInstance();
                    }
                } catch (ClassNotFoundException e) {
                    throw new MybatisPlusException("Class :" + dialectClazz + " is not found");
                }
            }
        }
        /* 未配置方言则抛出异常 */
        if (dialect == null) {
            throw new MybatisPlusException("The value of the dialect property in mybatis configuration.xml is not defined.");
        }
        return dialect;
    }

    /**
     * <p>
     * 根据数据库类型选择不同分页方言
     * </p>
     *
     * @param dbType 数据库类型
     * @return
     * @throws Exception
     */
    private static IDialect getDialectByDbtype(DBType dbType) {
        switch (dbType) {
            case MYSQL:
                return MySqlDialect.INSTANCE;
            case ORACLE:
                return OracleDialect.INSTANCE;
            case DB2:
                return DB2Dialect.INSTANCE;
            case H2:
                return H2Dialect.INSTANCE;
            case SQLSERVER:
                return SQLServerDialect.INSTANCE;
            case SQLSERVER2005:
                return SQLServer2005Dialect.INSTANCE;
            case POSTGRE:
                return PostgreDialect.INSTANCE;
            case HSQL:
                return HSQLDialect.INSTANCE;
            case SQLITE:
                return SQLiteDialect.INSTANCE;
            default:
                throw new MybatisPlusException("Error: Unknown database type, or do not support changing database!\n");
        }
    }

}
