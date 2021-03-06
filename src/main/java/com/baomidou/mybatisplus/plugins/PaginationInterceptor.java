package com.baomidou.mybatisplus.plugins;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.RowBounds;

import com.baomidou.mybatisplus.MybatisDefaultParameterHandler;
import com.baomidou.mybatisplus.enums.DBType;
import com.baomidou.mybatisplus.plugins.pagination.DialectFactory;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.toolkit.JdbcUtils;
import com.baomidou.mybatisplus.toolkit.JsqlParserUtils;
import com.baomidou.mybatisplus.toolkit.PluginUtils;
import com.baomidou.mybatisplus.toolkit.StringUtils;

/**
 * <p>
 * 分页拦截器
 * </p>
 *
 * @author hubin
 * @Date 2016-01-23
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class PaginationInterceptor implements Interceptor {

    /* 溢出总页数，设置第一页 */
    private boolean overflowCurrent = false;

    /**
     * Physical Pagination Interceptor for all the queries with parameter {@link org.apache.ibatis.session.RowBounds}
     */
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) PluginUtils.realTarget(invocation.getTarget());
        MetaObject metaStatementHandler = SystemMetaObject.forObject(statementHandler);
        // 先判断是不是SELECT操作
        MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
        if (!SqlCommandType.SELECT.equals(mappedStatement.getSqlCommandType())) {
            return invocation.proceed();
        }
        RowBounds rowBounds = (RowBounds) metaStatementHandler.getValue("delegate.rowBounds");
        /* 不需要分页的场合 */
        if (rowBounds == null || rowBounds == RowBounds.DEFAULT) {
            return invocation.proceed();
        }
        BoundSql boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");
        String originalSql = boundSql.getSql();
        Connection connection = (Connection) invocation.getArgs()[0];
        DBType dbType = JdbcUtils.getDbType(connection.getMetaData().getURL());
        if (rowBounds instanceof Pagination) {
            Pagination page = (Pagination) rowBounds;
            if (page.isSearchCount()) {
                this.queryTotal(JsqlParserUtils.jsqlparserCount(originalSql), mappedStatement, boundSql, page, connection);
                if (page.getTotal() <= 0) {
                    return invocation.proceed();
                }
            }
            originalSql = DialectFactory.buildPaginationSql(page, originalSql, dbType, null);
        } else {
            // support physical Pagination for RowBounds
            originalSql = DialectFactory.buildPaginationSql(rowBounds, originalSql, dbType, null);
        }

		/*
         * <p> 禁用内存分页 </p> <p> 内存分页会查询所有结果出来处理（这个很吓人的），如果结果变化频繁这个数据还会不准。</p>
		 */
        metaStatementHandler.setValue("delegate.boundSql.sql", originalSql);
        metaStatementHandler.setValue("delegate.rowBounds.offset", RowBounds.NO_ROW_OFFSET);
        metaStatementHandler.setValue("delegate.rowBounds.limit", RowBounds.NO_ROW_LIMIT);
        return invocation.proceed();
    }

    /**
     * 查询总记录条数
     *
     * @param sql
     * @param mappedStatement
     * @param boundSql
     * @param page
     */
    protected void queryTotal(String sql, MappedStatement mappedStatement, BoundSql boundSql, Pagination page, Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            DefaultParameterHandler parameterHandler = new MybatisDefaultParameterHandler(mappedStatement, boundSql.getParameterObject(), boundSql);
            parameterHandler.setParameters(statement);
            int total = 0;
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    total = resultSet.getInt(1);
                }
            }
            page.setTotal(total);
            /*
             * 溢出总页数，设置第一页
			 */
            int pages = page.getPages();
            if (overflowCurrent && (page.getCurrent() > pages)) {
                page = new Pagination(1, page.getSize());
                page.setTotal(total);
            }
        }
    }

    public Object plugin(Object target) {
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    @Override
    public void setProperties(Properties prop) {
        String overflowCurrent = prop.getProperty("overflowCurrent");
        if (StringUtils.isNotEmpty(overflowCurrent)) {
            this.overflowCurrent = Boolean.TRUE.toString().equals(overflowCurrent);
        }
    }


    public void setOverflowCurrent(boolean overflowCurrent) {
        this.overflowCurrent = overflowCurrent;
    }

}
