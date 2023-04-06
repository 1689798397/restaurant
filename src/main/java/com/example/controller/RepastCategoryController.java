package com.example.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.vo.ResultVO;
import com.example.domain.RepastCategory;
import com.example.service.RepastCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "菜品类别")
@RestController
@RequestMapping("/repastCategory")
@SaCheckPermission("admin")
public class RepastCategoryController {

    @Autowired
    private RepastCategoryService repastCategoryService;

    @ApiOperation("添加菜品类别")
    @ApiImplicitParams({@ApiImplicitParam(name = "repastId", value = "菜品类别id", required = true),@ApiImplicitParam(name = "repastCategoryName", value = "菜品类别名字", required = true), @ApiImplicitParam(name = "price", value = "菜品类别价格", required = true)})
    @PostMapping("/add")
    public ResultVO add(RepastCategory repastCategory) {
        if (repastCategory == null || !StringUtils.hasText(repastCategory.getRepastCategoryName()) || repastCategory.getPrice() == null) {
            return ResultVO.error("非法输入！");
        }
        repastCategoryService.save(repastCategory);
        return ResultVO.ok();
    }

    @ApiOperation("通过菜品类别id删除菜品类别")
    @ApiImplicitParam(name = "repastCategoryId", value = "菜品类别id", required = true)
    @PostMapping("/delete")
    public ResultVO deleteById(Integer repastCategoryId) {
        repastCategoryService.removeById(repastCategoryId);
        return ResultVO.ok();
    }

    @ApiOperation("通过菜品类别id批量删除菜品类别")
    @ApiImplicitParam(name = "repastCategoryIds", value = "菜品类别id列表", required = true)
    @PostMapping("/deleteByIds")
    public ResultVO deleteByIds(List<Integer> repastCategoryIds) {
        repastCategoryService.removeByIds(repastCategoryIds);
        return ResultVO.ok();
    }

    @ApiOperation("更改菜品类别")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "菜品类别id", required = true), @ApiImplicitParam(name = "repastCategoryName", value = "菜品类别名字", required = true), @ApiImplicitParam(name = "price", value = "菜品类别价格", required = true)})
    @PostMapping("/update")
    public ResultVO updateById(RepastCategory repastCategory) {
        repastCategoryService.updateById(repastCategory);
        return ResultVO.ok();
    }

    @ApiOperation("菜品类别列表")
    @ApiImplicitParam(name = "repastId", value = "菜品id", required = true)
    @PostMapping("/list")
    public ResultVO detail(Integer repastId) {
        QueryWrapper<RepastCategory> repastCategoryQueryWrapper = new QueryWrapper<>();
        repastCategoryQueryWrapper.eq("repast_id", repastId);
        List<RepastCategory> repastCategoryList = repastCategoryService.list(repastCategoryQueryWrapper);
        return ResultVO.ok(repastCategoryList);
    }
}
