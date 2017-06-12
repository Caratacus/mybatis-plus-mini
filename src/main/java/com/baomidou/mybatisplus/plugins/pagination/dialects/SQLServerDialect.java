package com.baomidou.mybatisplus.plugins.pagination.dialects;

import com.baomidou.mybatisplus.plugins.pagination.IDialect;

/**
 * <p>
 * SQLServer 数据库分页语句组装实现
 * </p>
 *
 * @author hubin
 * @Date 2016-03-23
 */
public class SQLServerDialect implements IDialect {

    public static final SQLServerDialect INSTANCE = new SQLServerDialect();

    public String buildPaginationSql(String originalSql, int offset, int limit) {
        StringBuilder sql = new StringBuilder(originalSql);
        sql.append(" OFFSET ").append(offset).append(" ROWS FETCH NEXT ");
        sql.append(limit).append(" ROWS ONLY");
        return sql.toString();
    }
}
