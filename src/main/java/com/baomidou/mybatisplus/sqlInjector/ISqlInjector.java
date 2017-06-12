package com.baomidou.mybatisplus.sqlInjector;

import org.apache.ibatis.builder.MapperBuilderAssistant;

/**
 * <p>
 * SQL 自动注入器接口
 * </p>
 *
 * @author hubin
 * @Date 2016-07-24
 */
public interface ISqlInjector {

    /**
     * 根据mapperClass注入SQL
     *
     * @param builderAssistant
     * @param mapperClass
     */
    void inject(MapperBuilderAssistant builderAssistant, Class<?> mapperClass);

    /**
     * 检查SQL是否注入(已经注入过不再注入)
     *
     * @param builderAssistant
     * @param mapperClass
     */
    void inspectInject(MapperBuilderAssistant builderAssistant, Class<?> mapperClass);

}
