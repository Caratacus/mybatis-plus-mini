package com.baomidou.mybatisplus.entity;

import java.io.Serializable;

import org.apache.ibatis.session.SqlSessionFactory;

import com.baomidou.mybatisplus.enums.DBType;
import com.baomidou.mybatisplus.toolkit.GlobalConfigUtils;
import com.baomidou.mybatisplus.toolkit.StringUtils;

/**
 * <p>
 * 查询字段
 * </p>
 *
 * @author Caratacus
 * @Date 2017-04-27
 */
public class Column implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String AS = " AS ";

    // 默认FACTORY
    public static SqlSessionFactory FACTORY;
    //转义
    private boolean escape = true;
    //字段
    private String column;
    //AS
    private String as;

    /**
     * 获取实例
     */
    public static Column create() {
        return new Column();
    }

    public String getColumn() {
        return column;
    }

    public Column column(String column) {
        this.column = column;
        return this;
    }

    public String getAs() {
        if (StringUtils.isEmpty(getColumn()) || StringUtils.isEmpty(as)) {
            return StringUtils.EMPTY;
        }
        String quote = null;
        if (isEscape() && FACTORY != null) {
            GlobalConfiguration globalConfig = GlobalConfigUtils.getGlobalConfig(FACTORY.getConfiguration());
            quote = globalConfig.getIdentifierQuote() == null ? DBType.getQuote(globalConfig.getDbType()) : globalConfig.getIdentifierQuote();
        }
        return AS + (StringUtils.isNotEmpty(quote) ? String.format(quote, as) : as);
    }

    public Column as(String as) {
        this.as = as;
        return this;
    }

    public boolean isEscape() {
        return escape;
    }

    public void setEscape(boolean escape) {
        this.escape = escape;
    }
}
