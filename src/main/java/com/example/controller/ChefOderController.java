package com.example.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.vo.ResultVO;
import com.example.domain.COrder;
import com.example.service.COrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "厨师任务")
@SaCheckPermission("chef")
@RestController
@RequestMapping("/chef/order")
public class ChefOderController {
    @Autowired
    private COrderService cOrderService;

    @ApiOperation("标记完成任务")
    @ApiImplicitParam(name = "orderId", value = "订单id", required = true)
    @PostMapping("/complete")
    private ResultVO complete(Integer orderId) {
        COrder order = new COrder();
        order.setId(orderId);
        order.setTransactionStatus(2);
        cOrderService.save(order);
        return ResultVO.ok();
    }

    @ApiOperation("获取所有任务")
    @ApiImplicitParams({@ApiImplicitParam(name = "pageNum", value = "页面号"), @ApiImplicitParam(name = "pageSize", value = "页面大小")})
    @PostMapping("/list")
    private ResultVO list(Long pageNum, Long pageSize) {
        if (pageNum == null) {
            pageNum = 1L;
        }
        if (pageSize == null) {
            pageSize = 10L;
        }
        QueryWrapper<COrder> cOrderQueryWrapper = new QueryWrapper<>();
        cOrderQueryWrapper.eq("transaction_status", 1);
        cOrderQueryWrapper.orderBy(true, true, "generation_date");
        Page<COrder> cOrderPage = new Page<>(pageNum, pageSize);
        Page<COrder> page = cOrderService.page(cOrderPage, cOrderQueryWrapper);
        for (COrder order : page.getRecords()) {
            order.setUserId(null);
            order.setSeatNumber(null);
            order.setTotalAmount(null);
        }
        return ResultVO.ok(page);
    }
}
