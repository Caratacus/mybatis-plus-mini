package com.baomidou.mybatisplus.plugins;

import org.apache.ibatis.reflection.MetaObject;

import com.baomidou.mybatisplus.mapper.MetaObjectHandler;

/**
 * <p>
 * 默认填充器关闭操作
 * </p>
 *
 * @author hubin
 * @since 2017-04-19
 */
public class DefaultMetaObjectHandler extends MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {

    }

    @Override
    public void updateFill(MetaObject metaObject) {

    }

    @Override
    public boolean openInsertFill() {
        return false;
    }

    @Override
    public boolean openUpdateFill() {
        return false;
    }

}
