package com.baomidou.mybatisplus.toolkit;

import java.util.ArrayList;
import java.util.List;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.select.Distinct;
import net.sf.jsqlparser.statement.select.OrderByElement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;

/**
 * <p>
 * JsqlParserUtils工具类
 * </p>
 *
 * @author Caratacus
 * @Date 2016-11-30
 */
public class JsqlParserUtils {

    private static List<SelectItem> countSelectItem = null;

    /**
     * jsqlparser方式获取select的count语句
     *
     * @param originalSql selectSQL
     * @return
     */
    public static String jsqlparserCount(String originalSql) {
        String sqlCount;
        try {
            Select selectStatement = (Select) CCJSqlParserUtil.parse(originalSql);
            PlainSelect plainSelect = (PlainSelect) selectStatement.getSelectBody();
            Distinct distinct = plainSelect.getDistinct();
            List<Expression> groupBy = plainSelect.getGroupByColumnReferences();
            // 优化Order by
            List<OrderByElement> orderBy = plainSelect.getOrderByElements();
            // 添加包含groupby 不去除orderby
            if (CollectionUtils.isEmpty(groupBy) && CollectionUtils.isNotEmpty(orderBy)) {
                plainSelect.setOrderByElements(null);
            }
            // 包含 distinct、groupBy不优化
            if (distinct != null || CollectionUtils.isNotEmpty(groupBy)) {
                sqlCount = String.format(SqlUtils.SQL_BASE_COUNT, selectStatement.toString());
                return sqlCount;
            }
            List<SelectItem> selectCount = countSelectItem();
            plainSelect.setSelectItems(selectCount);
            sqlCount = selectStatement.toString();
        } catch (Exception e) {
            sqlCount = String.format(SqlUtils.SQL_BASE_COUNT, originalSql);
        }
        return sqlCount;
    }

    /**
     * 获取jsqlparser中count的SelectItem
     *
     * @return
     */
    private static List<SelectItem> countSelectItem() {
        if (CollectionUtils.isNotEmpty(countSelectItem)) {
            return countSelectItem;
        }
        Function function = new Function();
        function.setName("COUNT");
        List<Expression> expressions = new ArrayList<>();
        LongValue longValue = new LongValue(1);
        ExpressionList expressionList = new ExpressionList();
        expressions.add(longValue);
        expressionList.setExpressions(expressions);
        function.setParameters(expressionList);
        countSelectItem = new ArrayList<>();
        SelectExpressionItem selectExpressionItem = new SelectExpressionItem(function);
        countSelectItem.add(selectExpressionItem);
        return countSelectItem;
    }
}
