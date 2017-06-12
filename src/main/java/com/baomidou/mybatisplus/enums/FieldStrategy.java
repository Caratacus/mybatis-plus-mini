package com.baomidou.mybatisplus.enums;

import com.baomidou.mybatisplus.exceptions.MybatisPlusException;

/**
 * <p>
 * 字段策略枚举类
 * </p>
 *
 * @author Caratacus
 * @Date 2017-05-22
 */
public enum FieldStrategy {
    IGNORED, NOT_NULL, NOT_EMPTY;

    /**
     * 获取枚举
     *
     * @param fieldStrategy
     * @return
     */
    public static FieldStrategy getFieldStrategy(String fieldStrategy) {
        FieldStrategy[] fieldStrategies = FieldStrategy.values();
        for (FieldStrategy strategy : fieldStrategies) {
            if (strategy.name().equalsIgnoreCase(fieldStrategy)) {
                return strategy;
            }
        }
        throw new MybatisPlusException("Error: Unknown fieldStrategy type, or do not support changing fieldStrategy!\n");
    }

}
