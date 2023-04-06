package com.example.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 菜品
 * @TableName ingredient
 */
@TableName(value ="ingredient")
@Data
public class Ingredient implements Serializable {
    /**
     * 菜品配料id
     */
    @TableId
    private Integer id;

    /**
     * 菜品id
     */
    private Integer repastId;

    /**
     * 菜品配料名字
     */
    private String ingredientName;

    /**
     * 注意事项
     */
    private String note;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}