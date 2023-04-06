package com.example.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 菜品类别
 * @TableName repast_category
 */
@TableName(value ="repast_category")
@Data
public class RepastCategory implements Serializable {
    /**
     * 菜品类别id
     */
    @TableId
    private Integer id;

    /**
     * 菜品id
     */
    private Integer repastId;

    /**
     * 菜品类别名字
     */
    private String repastCategoryName;

    /**
     * 菜品价格
     */
    private BigDecimal price;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}