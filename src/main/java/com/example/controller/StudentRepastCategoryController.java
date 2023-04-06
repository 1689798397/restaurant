package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.vo.ResultVO;
import com.example.domain.RepastCategory;
import com.example.service.RepastCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "学生菜品分类接口")
@RestController
@RequestMapping("/student/repastCategory")
public class StudentRepastCategoryController {
    @Autowired
    private RepastCategoryService repastCategoryService;

    @ApiOperation("学生菜品分类列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "repastId", value = "菜品id", required = true), @ApiImplicitParam(name = "pageNum", value = "页面号"), @ApiImplicitParam(name = "pageSize", value = "页面大小")})
    @PostMapping("/list")
    public ResultVO list(Integer repastId, Long pageNum, Long pageSize) {
        if (pageNum == null) {
            pageNum = 1L;
        }
        if (pageSize == null) {
            pageSize = 10L;
        }
        Page<RepastCategory> repastCategoryPage = new Page<>(pageNum, pageSize);
        QueryWrapper<RepastCategory> repastCategoryQueryWrapper = new QueryWrapper<>();
        Page<RepastCategory> page = repastCategoryService.page(repastCategoryPage, repastCategoryQueryWrapper);
        return ResultVO.ok(page);
    }
}
