package com.baomidou.mybatisplus.incrementer;


/**
 * <p>
 * 表关键词 key 生成器接口
 * </p>
 *
 * @author Caratacus
 * @Date 2017-05-08
 */
public interface Incrementer {

    /**
     * <p>
     * 获取序列sql
     * </p>
     *
     * @param incrementerName 序列名称
     * @return
     */
    String getSequenceQuery(String incrementerName);

}
