/**
 * Copyright (c) 2011-2020, hubin (jobob@qq.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.baomidou.mybatisplus.toolkit;

import com.baomidou.mybatisplus.entity.CountOptimize;
import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

/**
 * <p>
 * SqlUtils工具类
 * </p>
 *
 * @author Caratacus
 * @Date 2016-11-13
 */
public class SqlUtils {

    public static final String SQL_BASE_COUNT = "SELECT COUNT(1) FROM ( %s ) TOTAL";
    private final static SqlFormatter sqlFormatter = new SqlFormatter();

    /**
     * 获取CountOptimize
     *
     * @param originalSql 需要计算Count SQL
     * @return CountOptimize
     */
    public static CountOptimize getCountOptimize(String originalSql) {
        return JsqlParserUtils.jsqlparserCount(originalSql);
    }

    /**
     * 查询SQL拼接Order By
     *
     * @param originalSql 需要拼接的SQL
     * @param page        page对象
     * @param orderBy     是否需要拼接Order By
     * @return
     */
    public static String concatOrderBy(String originalSql, Pagination page, boolean orderBy) {
        if (orderBy && StringUtils.isNotEmpty(page.getOrderByField()) && page.isOpenSort()) {
            StringBuilder buildSql = new StringBuilder(originalSql);
            buildSql.append(" ORDER BY ").append(page.getOrderByField());
            buildSql.append(page.isAsc() ? " ASC " : " DESC ");
            return buildSql.toString();
        }
        return originalSql;
    }

    /**
     * 格式sql
     *
     * @param boundSql
     * @param format
     * @return
     */
    public static String sqlFormat(String boundSql, boolean format) {
        if (format) {
            return sqlFormatter.format(boundSql);
        } else {
            return boundSql.replaceAll("[\\s]+", " ");
        }
    }

    /**
     * <p>
     * 用%连接like
     * </p>
     *
     * @param str 原字符串
     * @return
     */
    public static String concatLike(String str, SqlLike type) {
        StringBuilder builder = new StringBuilder(str.length() + 3);
        switch (type) {
            case LEFT:
                builder.append("%").append(str);
                break;
            case RIGHT:
                builder.append(str).append("%");
                break;
            case CUSTOM:
                builder.append(str);
                break;
            default:
                builder.append("%").append(str).append("%");
        }
        return builder.toString();
    }

}
