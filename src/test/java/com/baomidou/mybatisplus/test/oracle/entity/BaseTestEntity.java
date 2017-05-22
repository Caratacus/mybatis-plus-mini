package com.baomidou.mybatisplus.test.oracle.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.KeySequence;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;

/**
 * 用户表
 */
@KeySequence("SEQ_TEST")
public class BaseTestEntity implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "ID")
    private Long id;


    public BaseTestEntity() {

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}
