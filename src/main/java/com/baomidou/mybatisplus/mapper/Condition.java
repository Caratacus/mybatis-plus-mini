package com.baomidou.mybatisplus.mapper;

import com.baomidou.mybatisplus.toolkit.StringUtils;

/**
 * <p>
 * 条件查询构造器
 * </p>
 *
 * @author hubin Caratacus
 * @date 2016-11-7
 */
@SuppressWarnings({"rawtypes", "serial"})
public class Condition extends Wrapper {

    /**
     * 构建一个Empty条件构造 避免传递参数使用null
     */
    public static final Condition EMPTY = Condition.create();

    /**
     * 获取实例
     */
    public static Condition create() {
        return new Condition();
    }

    @Override
    public Object getEntity() {
        return null;
    }

    /**
     * SQL 片段
     */
    @Override
    public String getSqlSegment() {
        if (SqlHelper.isEmptyOfWrapper(this)) {
            return null;
        }
        /*
         * 无条件
		 */
        String sqlWhere = sql.toString();
        if (StringUtils.isEmpty(sqlWhere)) {
            return null;
        }
        /*
         * 根据当前实体判断是否需要将WHERE替换成 AND 增加实体不为空但所有属性为空的情况
		 */
        return isWhere != null ? (isWhere ? sqlWhere : sqlWhere.replaceFirst("WHERE", AND_OR)) : sqlWhere.replaceFirst("WHERE", AND_OR);

    }
}
