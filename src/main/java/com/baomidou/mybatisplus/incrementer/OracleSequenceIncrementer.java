package com.baomidou.mybatisplus.incrementer;

/**
 * <p>
 * Oracle Sequence
 * </p>
 *
 * @author Caratacus
 * @Date 2017-06-12
 */
public class OracleSequenceIncrementer implements Incrementer {

    @Override
    public String getSequenceQuery(String incrementerName) {
        return "SELECT " + incrementerName + ".NEXTVAL FROM DUAL";
    }

}
