package com.baomidou.mybatisplus.toolkit;

import com.baomidou.mybatisplus.enums.DBType;
import com.baomidou.mybatisplus.exceptions.MybatisPlusException;

/**
 * <p>
 * JDBC 工具类
 * </p>
 *
 * @author nieqiurong
 * @Date 2016-12-05
 */
public class JdbcUtils {

    /**
     * <p>
     * 根据连接地址判断数据库类型
     * </p>
     *
     * @param jdbcUrl 连接地址
     * @return
     */
    public static DBType getDbType(String jdbcUrl) {
        if (StringUtils.isEmpty(jdbcUrl)) {
            return DBType.MYSQL;
        }
        if (jdbcUrl.startsWith("jdbc:mysql:") || jdbcUrl.startsWith("jdbc:cobar:")
                || jdbcUrl.startsWith("jdbc:log4jdbc:mysql:")) {
            return DBType.MYSQL;
        } else if (jdbcUrl.startsWith("jdbc:oracle:") || jdbcUrl.startsWith("jdbc:log4jdbc:oracle:")) {
            return DBType.ORACLE;
        } else if (jdbcUrl.startsWith("jdbc:microsoft:") || jdbcUrl.startsWith("jdbc:log4jdbc:microsoft:")) {
            return DBType.SQLSERVER;
        } else if (jdbcUrl.startsWith("jdbc:sqlserver:") || jdbcUrl.startsWith("jdbc:log4jdbc:sqlserver:")) {
            return DBType.SQLSERVER;
        } else if (jdbcUrl.startsWith("jdbc:postgresql:") || jdbcUrl.startsWith("jdbc:log4jdbc:postgresql:")) {
            return DBType.POSTGRE;
        } else if (jdbcUrl.startsWith("jdbc:hsqldb:") || jdbcUrl.startsWith("jdbc:log4jdbc:hsqldb:")) {
            return DBType.HSQL;
        } else if (jdbcUrl.startsWith("jdbc:db2:")) {
            return DBType.DB2;
        } else if (jdbcUrl.startsWith("jdbc:sqlite:")) {
            return DBType.SQLITE;
        } else if (jdbcUrl.startsWith("jdbc:h2:") || jdbcUrl.startsWith("jdbc:log4jdbc:h2:")) {
            return DBType.H2;
        } else {
            throw new MybatisPlusException("Error: Unknown database type, or do not support changing database!\n");
        }
    }

}
