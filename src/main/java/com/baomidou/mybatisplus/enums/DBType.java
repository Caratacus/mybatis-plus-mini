package com.baomidou.mybatisplus.enums;

import com.baomidou.mybatisplus.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.toolkit.StringUtils;

/**
 * <p>
 * MybatisPlus 数据库类型
 * </p>
 *
 * @author Caratacus
 * @Date 2017-05-22
 */
public enum DBType {

    MYSQL, ORACLE, DB2, H2, HSQL, SQLITE, POSTGRE, SQLSERVER2005, SQLSERVER;

    /**
     * <p>
     * 获取数据库类型
     * </p>
     *
     * @param type 数据库类型字符串
     * @return
     */
    public static DBType getDBType(String type) {
        if (StringUtils.isNotEmpty(type)) {
            DBType[] dbTypes = DBType.values();
            for (DBType dbType : dbTypes) {
                if (dbType.name().equalsIgnoreCase(type)) {
                    return dbType;
                }
            }
        }
        throw new MybatisPlusException("Error: Unknown database type, or do not support changing database!\n");
    }

    /**
     * 获取数据库转义字符
     *
     * @param dbType
     * @return
     */
    public static String getQuote(DBType dbType) {
        switch (dbType) {
            case H2:
                return null;
            case DB2:
                return null;
            case HSQL:
                return null;
            case MYSQL:
                return "`%s`";
            case ORACLE:
                return null;
            case SQLITE:
                return "`%s`";
            case POSTGRE:
                return "\"%s\"";
            case SQLSERVER:
                return "[%s]";
            case SQLSERVER2005:
                return "[%s]";
            default:
                return null;

        }
    }

}
