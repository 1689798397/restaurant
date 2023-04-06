package com.example.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName authority
 */
@TableName(value ="authority")
@Data
public class Authority implements Serializable {
    /**
     * 编号
     */
    @TableId
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 权限
     */
    private String authority;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}