package com.example.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.vo.ResultVO;
import com.example.domain.COrder;
import com.example.domain.Repast;
import com.example.domain.RepastCategory;
import com.example.domain.Student;
import com.example.service.COrderService;
import com.example.service.RepastCategoryService;
import com.example.service.RepastService;
import com.example.service.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

@Api(tags = "学生订餐接口")
@SaCheckLogin
@RestController
@RequestMapping("/student/order")
public class StudentOderController {
    @Autowired
    private COrderService cOrderService;
    @Autowired
    private RepastCategoryService repastCategoryService;
    @Autowired
    private RepastService repastService;
    @Autowired
    private StudentService studentService;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @ApiOperation("下订单")
    @ApiImplicitParams({@ApiImplicitParam(name = "repastCategoryId", value = "菜品类别id",required = true), @ApiImplicitParam(name = "amount", value = "数量",required = true), @ApiImplicitParam(name = "remarks", value = "备注")})
    @Transactional
    @PostMapping("/add")
    public ResultVO add(Integer repastCategoryId, Integer amount, String remarks) throws Exception {
        if (repastCategoryId == null || amount == null) {
            return ResultVO.error("非法输入！");
        }
        COrder cOrder = new COrder();
        cOrder.setAmount(amount);
        cOrder.setUserId(StpUtil.getLoginIdAsInt());
        cOrder.setGenerationDate(new Date());
        RepastCategory repastCategory = repastCategoryService.getById(repastCategoryId);
        cOrder.setRepastCategoryName(repastCategory.getRepastCategoryName());
        Repast repast = repastService.getById(repastCategory.getRepastId());
        cOrder.setRepastName(repast.getRepastName());
        cOrder.setTotalAmount(repastCategory.getPrice().multiply(new BigDecimal(cOrder.getAmount())));
        cOrder.setTransactionStatus(1);
        if (redisTemplate.opsForValue().get("SeatNumber") == null) {
            redisTemplate.opsForValue().set("SeatNumber", 1L);
            cOrder.setSeatNumber(1L);
        } else {
            Long increment = redisTemplate.opsForValue().increment("SeatNumber");
            cOrder.setSeatNumber(increment);
        }
        cOrderService.save(cOrder);
        Student student = studentService.getById(StpUtil.getLoginIdAsInt());
        student.setBalance(student.getBalance().subtract(cOrder.getTotalAmount()));
        if (student.getBalance().compareTo(new BigDecimal(0)) < 0) {
            throw new Exception();
        }

        studentService.updateById(student);
        return ResultVO.ok();
    }

    @ApiOperation("取消订单")
    @ApiImplicitParam(name = "orderId",value = "订单id",required = true)
    @Transactional
    @PostMapping("/cancel")
    public ResultVO cancel(Integer orderId) {
        COrder cOrder = cOrderService.getById(orderId);
        cOrder.setTransactionStatus(0);
        cOrder.setId(orderId);
        cOrderService.updateById(cOrder);
        Student student = studentService.getById(StpUtil.getLoginIdAsInt());
        student.setBalance(student.getBalance().add(cOrder.getTotalAmount()));
        studentService.updateById(student);
        return ResultVO.ok();
    }

    @ApiOperation("查询订单")
    @ApiImplicitParams({@ApiImplicitParam(name = "pageNum", value = "页面号"), @ApiImplicitParam(name = "pageSize", value = "页面大小")})
    @PostMapping("/list")
    public ResultVO list(Long pageNum,Long pageSize) {
        if (pageNum == null) {
            pageNum = 1L;
        }
        if (pageSize == null) {
            pageSize = 10L;
        }
        Page<COrder> cOrderPage = new Page<>(pageNum, pageSize);
        QueryWrapper<COrder> cOrderQueryWrapper = new QueryWrapper<>();
        cOrderQueryWrapper.eq("user_id", StpUtil.getLoginIdAsInt());
        Page<COrder> page = cOrderService.page(cOrderPage, cOrderQueryWrapper);
        return ResultVO.ok(page);
    }
}
