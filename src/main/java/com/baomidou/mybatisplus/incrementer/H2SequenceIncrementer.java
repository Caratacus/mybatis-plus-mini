package com.baomidou.mybatisplus.incrementer;

/**
 * <p>
 * H2 Sequence
 * </p>
 *
 * @author Caratacus
 * @Date 2017-06-12
 */
public class H2SequenceIncrementer implements Incrementer {

    @Override
    public String getSequenceQuery(String incrementerName) {
        return "select " + incrementerName + ".nextval from dual";
    }


}
