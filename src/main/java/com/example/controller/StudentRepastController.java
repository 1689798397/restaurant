package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.vo.ResultVO;
import com.example.domain.Repast;
import com.example.service.RepastService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "学生菜品获取")
@RestController
@RequestMapping("/student/repast")
public class StudentRepastController {
    @Autowired
    private RepastService repastService;

    @ApiOperation("获取可下单菜品")
    @ApiImplicitParams({@ApiImplicitParam(name = "pageNum", value = "页面号"), @ApiImplicitParam(name = "pageSize", value = "页面大小")})
    @PostMapping("/list")
    public ResultVO list(Long pageNum, Long pageSize) {
        QueryWrapper<Repast> repastQueryWrapper = new QueryWrapper<>();
        repastQueryWrapper.eq("sales_status",1);
        if (pageNum == null) {
            pageNum = 1L;
        }
        if (pageSize == null) {
            pageSize = 10L;
        }
        Page<Repast> repastPage = new Page<>(pageNum, pageSize);
        Page<Repast> page = repastService.page(repastPage, repastQueryWrapper);
        return ResultVO.ok(page);
    }
}
