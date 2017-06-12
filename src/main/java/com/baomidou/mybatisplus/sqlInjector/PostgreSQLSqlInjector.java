package com.baomidou.mybatisplus.sqlInjector;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.mapping.SqlSource;

import com.baomidou.mybatisplus.entity.GlobalConfiguration;
import com.baomidou.mybatisplus.entity.TableFieldInfo;
import com.baomidou.mybatisplus.entity.TableInfo;
import com.baomidou.mybatisplus.enums.SqlMethod;
import com.baomidou.mybatisplus.toolkit.GlobalConfigUtils;
import com.baomidou.mybatisplus.toolkit.StringUtils;

/**
 * <p>
 * PostgreSQLSqlInjector 自动注入器
 * </p>
 *
 * @author Caratacus
 * @Date 2017-06-01
 */
public class PostgreSQLSqlInjector extends AutoSqlInjector {


    /**
     * <p>
     * 注入EntityWrapper方式查询记录列表 SQL 语句
     * </p>
     *
     * @param sqlMethod
     * @param mapperClass
     * @param modelClass
     * @param table
     */
    protected void injectSelectMapsSql(SqlMethod sqlMethod, Class<?> mapperClass, Class<?> modelClass, TableInfo table) {
        String sql = String.format(sqlMethod.getSql(), sqlSelectColumns(table, true), table.getTableName(),
                sqlWhereEntityWrapper(table));
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        this.addSelectMappedStatement(mapperClass, sqlMethod.getMethod(), sqlSource, Map.class, table);
    }

    /**
     * <p>
     * 获取需要转义的SQL字段
     * </p>
     *
     * @param convertStr
     * @return
     */
    protected String asSqlWordConvert(String convertStr) {
        GlobalConfiguration globalConfig = GlobalConfigUtils.getGlobalConfig(configuration);
        return String.format(globalConfig.getIdentifierQuote(), convertStr);
    }


    /**
     * <p>
     * SQL 查询所有表字段
     * </p>
     *
     * @param table
     * @param entityWrapper 是否为包装类型查询
     * @return
     */
    protected String sqlSelectColumns(TableInfo table, boolean entityWrapper) {
        StringBuilder columns = new StringBuilder();
        if (null != table.getResultMap()) {
            /*
             * 存在 resultMap 映射返回
			 */
            if (entityWrapper) {
                columns.append("<choose><when test=\"ew != null and ew.sqlSelect != null\">${ew.sqlSelect}</when><otherwise>");
            }
            columns.append("*");
            if (entityWrapper) {
                columns.append("</otherwise></choose>");
            }
        } else {
            /*
             * 普通查询
			 */
            if (entityWrapper) {
                columns.append("<choose><when test=\"ew != null and ew.sqlSelect != null\">${ew.sqlSelect}</when><otherwise>");
            }
            List<TableFieldInfo> fieldList = table.getFieldList();
            int _size = 0;
            if (null != fieldList) {
                _size = fieldList.size();
            }

            // 主键处理
            if (StringUtils.isNotEmpty(table.getKeyProperty())) {
                if (table.isKeyRelated()) {
                    columns.append(table.getKeyColumn()).append(" AS ").append(asSqlWordConvert(table.getKeyProperty()));
                } else {
                    columns.append(sqlWordConvert(table.getKeyProperty()));
                }
                if (_size >= 1) {
                    // 判断其余字段是否存在
                    columns.append(",");
                }
            }

            if (_size >= 1) {
                // 字段处理
                int i = 0;
                Iterator<TableFieldInfo> iterator = fieldList.iterator();
                while (iterator.hasNext()) {
                    TableFieldInfo fieldInfo = iterator.next();
                    // 匹配转换内容
                    String wordConvert = sqlWordConvert(fieldInfo.getProperty());
                    if (fieldInfo.getColumn().equals(wordConvert)) {
                        columns.append(wordConvert);
                    } else {
                        // 字段属性不一致
                        columns.append(fieldInfo.getColumn());
                        columns.append(" AS ").append(asSqlWordConvert(wordConvert));
                    }
                    if (i + 1 < _size) {
                        columns.append(",");
                    }
                    i++;
                }
            }
            if (entityWrapper) {
                columns.append("</otherwise></choose>");
            }
        }

		/*
         * 返回所有查询字段内容
		 */
        return columns.toString();
    }

}
