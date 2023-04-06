package com.example.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 厨师账户信息
 * @TableName chef
 */
@TableName(value ="chef")
@Data
public class Chef implements Serializable {
    /**
     * 厨师id
     */
    @TableId
    private Integer id;

    /**
     * 厨师账号
     */
    private String userName;

    /**
     * 厨师密码
     */
    private String passWord;

    /**
     * 厨师昵称
     */
    private String nickName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}