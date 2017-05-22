/**
 * Copyright (c) 2011-2014, hubin (jobob@qq.com).
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
