package com.example.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 学生信息
 * @TableName student
 */
@TableName(value ="student")
@Data
public class Student implements Serializable {
    /**
     * 学生id
     */
    @TableId
    private Integer id;

    /**
     * 学生名字
     */
    private String userName;

    /**
     * 学生密码
     */
    private String passWord;

    /**
     * 学生昵称
     */
    private String nickName;

    /**
     * 余额
     */
    private BigDecimal balance;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}