package com.baomidou.mybatisplus.incrementer;

/**
 * <p>
 * Postgre Sequence
 * </p>
 *
 * @author Caratacus
 * @Date 2017-06-12
 */
public class PostgreSequenceIncrementer implements Incrementer {

    @Override
    public String getSequenceQuery(String incrementerName) {
        return "select nextval('" + incrementerName + "')";
    }

}
