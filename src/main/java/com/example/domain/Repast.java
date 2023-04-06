package com.example.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 菜品
 * @TableName repast
 */
@TableName(value ="repast")
@Data
public class Repast implements Serializable {
    /**
     * 菜品id
     */
    @TableId
    private Integer id;

    /**
     * 菜品名字
     */
    private String repastName;

    /**
     * 默认价格
     */
    private BigDecimal price;

    /**
     * 菜品描述
     */
    private String info;

    /**
     * 菜品图片地址
     */
    private String imgUrl;

    /**
     * 售卖状态：0表示停止售卖 1表示售卖
     */
    private Integer salesStatus;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}