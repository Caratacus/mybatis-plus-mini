package com.baomidou.mybatisplus;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;

/**
 * <p>
 * 继承 XMLLanguageDriver 重装构造函数，使用自定义 ParameterHandler
 * </p>
 *
 * @author hubin
 * @Date 2016-03-11
 */
public class MybatisXMLLanguageDriver extends XMLLanguageDriver {

    @Override
    public ParameterHandler createParameterHandler(MappedStatement mappedStatement, Object parameterObject,
                                                   BoundSql boundSql) {
        /* 使用自定义 ParameterHandler */
        return new MybatisDefaultParameterHandler(mappedStatement, parameterObject, boundSql);
    }
}
