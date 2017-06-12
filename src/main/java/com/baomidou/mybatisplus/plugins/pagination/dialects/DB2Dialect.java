package com.baomidou.mybatisplus.plugins.pagination.dialects;

import com.baomidou.mybatisplus.plugins.pagination.IDialect;

/**
 * <p>
 * DB2 数据库分页方言
 * </p>
 *
 * @author hubin
 * @Date 2016-11-10
 */
public class DB2Dialect implements IDialect {

    public static final DB2Dialect INSTANCE = new DB2Dialect();

    private static String getRowNumber(String originalSql) {
        StringBuilder rownumber = new StringBuilder(50).append("rownumber() over(");
        int orderByIndex = originalSql.toLowerCase().indexOf("order by");
        if (orderByIndex > 0 && !hasDistinct(originalSql)) {
            rownumber.append(originalSql.substring(orderByIndex));
        }
        rownumber.append(") as rownumber_,");
        return rownumber.toString();
    }

    private static boolean hasDistinct(String originalSql) {
        return originalSql.toLowerCase().contains("select distinct");
    }

    public String buildPaginationSql(String originalSql, int offset, int limit) {
        int startOfSelect = originalSql.toLowerCase().indexOf("select");
        StringBuilder pagingSelect = new StringBuilder(originalSql.length() + 100)
                .append(originalSql.substring(0, startOfSelect)).append("select * from ( select ")
                .append(getRowNumber(originalSql));
        if (hasDistinct(originalSql)) {
            pagingSelect.append(" row_.* from ( ").append(originalSql.substring(startOfSelect)).append(" ) as row_");
        } else {
            pagingSelect.append(originalSql.substring(startOfSelect + 6));
        }
        pagingSelect.append(" ) as temp_ where rownumber_ ");

        // add the restriction to the outer select
        if (offset > 0) {
            String endString = offset + "+" + limit;
            pagingSelect.append("between ").append(offset).append("+1 and ").append(endString);
        } else {
            pagingSelect.append("<= ").append(limit);
        }
        return null;
    }
}
