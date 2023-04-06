package com.example.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 管理员账号信息
 * @TableName admin
 */
@TableName(value ="admin")
@Data
public class Admin implements Serializable {
    /**
     * 管理员id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 管理员账号
     */
    private String userName;

    /**
     * 管理员密码
     */
    private String passWord;

    /**
     * 管理员昵称
     */
    private String nickName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}