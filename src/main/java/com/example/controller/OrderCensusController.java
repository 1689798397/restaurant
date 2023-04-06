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

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;

@Api(tags = "统计")
@RequestMapping("census")
@SaCheckPermission("admin")
@RestController
public class OrderCensusController {
    @Autowired
    private COrderService cOrderService;

    @ApiOperation("指定时间内所有订单和总金额")
    @ApiImplicitParams({@ApiImplicitParam(name = "startDate", value = "开始时间"), @ApiImplicitParam(name = "endDate", value = "结束时间"), @ApiImplicitParam(name = "pageNum", value = "页码数"), @ApiImplicitParam(name = "pageSize", value = "页面大小")})
    @PostMapping("/all")
    public ResultVO allOrder(Date startDate, Date endDate, Long pageNum, Long pageSize) throws ParseException {
        if (startDate == null || endDate == null) {
            return ResultVO.error();
        }
        if (pageNum == null) {
            pageNum = 1L;
        }
        if (pageSize == null) {
            pageSize = 10L;
        }
        QueryWrapper<COrder> cOrderQueryWrapper = new QueryWrapper<>();
        cOrderQueryWrapper.ge("generation_date", startDate);
        cOrderQueryWrapper.le("generation_date", endDate);
        Page<COrder> cOrderPage = new Page<>(pageNum, pageSize);
        Page<COrder> page = cOrderService.page(cOrderPage, cOrderQueryWrapper);
        BigDecimal count = new BigDecimal(0);
        for (COrder cOrder : page.getRecords()) {
            count = count.add(cOrder.getTotalAmount());
        }
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("page", page);
        hashMap.put("count", count);
        return ResultVO.ok(hashMap);
    }

    @ApiOperation("指定时间内成交的订单和总金额")
    @ApiImplicitParams({@ApiImplicitParam(name = "startDate", value = "开始时间"), @ApiImplicitParam(name = "endDate", value = "结束时间"), @ApiImplicitParam(name = "pageNum", value = "页码数"), @ApiImplicitParam(name = "pageSize", value = "页面大小")})
    @PostMapping("/sale")
    public ResultVO saleOrder(Date startDate, Date endDate, Long pageNum, Long pageSize) {
        if (startDate == null || endDate == null) {
            return ResultVO.error();
        }
        if (pageNum == null) {
            pageNum = 1L;
        }
        if (pageSize == null) {
            pageSize = 10L;
        }
        QueryWrapper<COrder> cOrderQueryWrapper = new QueryWrapper<>();
        cOrderQueryWrapper.eq("transaction_status", 2);
        cOrderQueryWrapper.ge("generation_date", startDate);
        cOrderQueryWrapper.le("generation_date", endDate);
        Page<COrder> cOrderPage = new Page<>(pageNum, pageSize);
        Page<COrder> page = cOrderService.page(cOrderPage, cOrderQueryWrapper);
        BigDecimal count = new BigDecimal(0);
        for (COrder cOrder : page.getRecords()) {
            count = count.add(cOrder.getTotalAmount());
        }
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("page", page);
        hashMap.put("count", count);
        return ResultVO.ok(hashMap);
    }

    @ApiOperation("指定时间内退款的订单和总金额")
    @ApiImplicitParams({@ApiImplicitParam(name = "startDate", value = "开始时间"), @ApiImplicitParam(name = "endDate", value = "结束时间"), @ApiImplicitParam(name = "pageNum", value = "页码数"), @ApiImplicitParam(name = "pageSize", value = "页面大小")})
    @PostMapping("/cancel")
    public ResultVO cancelOrder(Date startDate, Date endDate, Long pageNum, Long pageSize) {
        if (startDate == null || endDate == null) {
            return ResultVO.error();
        }
        if (pageNum == null) {
            pageNum = 1L;
        }
        if (pageSize == null) {
            pageSize = 10L;
        }
        QueryWrapper<COrder> cOrderQueryWrapper = new QueryWrapper<>();
        cOrderQueryWrapper.eq("transaction_status", 0);
        cOrderQueryWrapper.ge("generation_date", startDate);
        cOrderQueryWrapper.le("generation_date", endDate);
        Page<COrder> cOrderPage = new Page<>(pageNum, pageSize);
        Page<COrder> page = cOrderService.page(cOrderPage, cOrderQueryWrapper);
        BigDecimal count = new BigDecimal(0);
        for (COrder cOrder : page.getRecords()) {
            count = count.add(cOrder.getTotalAmount());
        }
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("page", page);
        hashMap.put("count", count);
        return ResultVO.ok(hashMap);
    }
}
