package com.baomidou.mybatisplus.entity;

import java.lang.reflect.Field;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.FieldStrategy;
import com.baomidou.mybatisplus.toolkit.SqlReservedWords;
import com.baomidou.mybatisplus.toolkit.StringUtils;

/**
 * <p>
 * 数据库表字段反射信息
 * </p>
 *
 * @author hubin sjy willenfoo tantan
 * @Date 2016-09-09
 */
public class TableFieldInfo {

    /**
     * <p>
     * 是否有存在字段名与属性名关联
     * </p>
     * true , false
     */
    private boolean related = false;

    /**
     * 字段名
     */
    private String column;

    /**
     * 属性名
     */
    private String property;

    /**
     * 属性表达式#{property}, 可以指定jdbcType, typeHandler等
     */
    private String el;
    /**
     * 属性类型
     */
    private String propertyType;

    /**
     * 字段策略【 默认，自判断 null 】
     */
    private FieldStrategy fieldStrategy = FieldStrategy.NOT_NULL;

    /**
     * 插入忽略
     */
    private boolean insertIgnore = false;

    /**
     * 更新忽略
     */
    private boolean updateIgnore = false;

    /**
     * <p>
     * 存在 TableField 注解构造函数
     * </p>
     */
    public TableFieldInfo(GlobalConfiguration globalConfig, String column,
                          String el, Field field, TableField tableField) {
        this.property = field.getName();
        this.propertyType = field.getType().getName();
        /*
         * 1、注解 value 不存在，开启字段下划线申明<br>
         * 2、没有开启下划线申明，但是column与property不等的情况<br>
         * 设置 related 为 true
         */
        if (StringUtils.isEmpty(tableField.value())
                && globalConfig.isDbColumnUnderline()) {
             /* 开启字段下划线申明 */
            this.related = true;
            this.setColumn(globalConfig, StringUtils.camelToUnderline(column));
        } else {
            this.setColumn(globalConfig, column);
            if (!column.equals(this.property)) {
                this.related = true;
            }
        }
        this.el = el;
        /*
         * 优先使用单个字段注解，否则使用全局配置<br>
         * 自定义字段验证策略 fixed-239
		 */
        if (FieldStrategy.NOT_NULL != tableField.validate()) {
            this.fieldStrategy = tableField.validate();
        } else {
            this.fieldStrategy = globalConfig.getFieldStrategy();
        }
        /*
         * 保存当前字段的插入忽略，更新忽略值
		 */
        this.insertIgnore = tableField.insertIgnore();
        this.updateIgnore = tableField.updateIgnore();
    }

    public TableFieldInfo(GlobalConfiguration globalConfig, Field field) {
        if (globalConfig.isDbColumnUnderline()) {
            /* 开启字段下划线申明 */
            this.related = true;
            this.setColumn(globalConfig, StringUtils.camelToUnderline(field.getName()));
        } else {
            this.setColumn(globalConfig, field.getName());
        }
        this.property = field.getName();
        this.el = field.getName();
        this.fieldStrategy = globalConfig.getFieldStrategy();
        this.propertyType = field.getType().getName();
    }


    public boolean isRelated() {
        return related;
    }

    public void setRelated(boolean related) {
        this.related = related;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(GlobalConfiguration globalConfig, String column) {
        String temp = SqlReservedWords.convert(globalConfig, column);
        if (globalConfig.isCapitalMode() && !isRelated()) {
            // 全局大写，非注解指定
            temp = temp.toUpperCase();
        }
        this.column = temp;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getEl() {
        return el;
    }

    public void setEl(String el) {
        this.el = el;
    }

    public FieldStrategy getFieldStrategy() {
        return fieldStrategy;
    }

    public void setFieldStrategy(FieldStrategy fieldStrategy) {
        this.fieldStrategy = fieldStrategy;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public boolean isInsertIgnore() {
        return insertIgnore;
    }

    public void setInsertIgnore(boolean insertIgnore) {
        this.insertIgnore = insertIgnore;
    }

    public boolean isUpdateIgnore() {
        return updateIgnore;
    }

    public void setUpdateIgnore(boolean updateIgnore) {
        this.updateIgnore = updateIgnore;
    }
}
