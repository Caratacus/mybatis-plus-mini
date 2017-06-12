package com.baomidou.mybatisplus.test.mysql;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 自定义 Mapper 接口
 * </p>
 *
 * @author hubin
 * @date 2017-03-14
 */
public interface MyBaseMapper<T> extends BaseMapper<T> {

    // 测试自定义 Mapper 接口

    // 这个类  不要放到  mapper 扫描目录，否则会当做真实 表 mapper 扫描异常！！

}
