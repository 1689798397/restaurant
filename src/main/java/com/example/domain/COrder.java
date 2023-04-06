package com.example.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 订单
 * @TableName c_order
 */
@TableName(value ="c_order")
@Data
public class COrder implements Serializable {
    /**
     * 订单id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 订单所属用户id
     */
    private Integer userId;

    /**
     * 菜品名称
     */
    private String repastName;

    /**
     * 菜品类别名称
     */
    private String repastCategoryName;

    /**
     * 数量
     */
    private Integer amount;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 订单总金额
     */
    private BigDecimal totalAmount;

    /**
     * 下单时间
     */
    private Date generationDate;

    /**
     * 成交状态：0表示退款1表示未退款2表示成交
     */
    private Integer transactionStatus;

    /**
     * 座位号
     */
    private Long seatNumber;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}