package com.baomidou.mybatisplus.enums;

import com.baomidou.mybatisplus.exceptions.MybatisPlusException;

/**
 * <p>
 * 生成ID类型枚举类
 * </p>
 *
 * @author hubin
 * @Date 2015-11-10
 */
public enum IdType {
    /**
     * 数据库ID自增
     */
    AUTO,
    /**
     * 用户输入ID
     */
    INPUT,
    /**
     * ID_WORKER
     */
    ID_WORKER,
    /**
     * UUID
     */
    UUID,
    /**
     * 未设置主键类型
     */
    NONE;

    /**
     * <p>
     * 主键策略
     * </p>
     *
     * @param idType ID 策略类型
     * @return
     */
    public static IdType getIdType(String idType) {
        IdType[] idTypes = IdType.values();
        for (IdType type : idTypes) {
            if (type.name().equalsIgnoreCase(idType)) {
                return type;
            }
        }
        throw new MybatisPlusException("Error: Unknown IdType type, or do not support changing IdType!\n");
    }

}
