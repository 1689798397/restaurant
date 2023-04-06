package com.example.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.vo.ResultVO;
import com.example.domain.Ingredient;
import com.example.service.IngredientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Api(tags = "配料")
@RestController
@RequestMapping("/ingredient")
public class IngredientController {
    @Autowired
    private IngredientService ingredientService;

    @ApiOperation("保存配料")
    @ApiImplicitParams({@ApiImplicitParam(name = "repastId", value = "菜品id", required = true), @ApiImplicitParam(name = "ingredients", value = "配料对象列表")})
    @SaCheckPermission("admin")
    @PostMapping("/save")
    public ResultVO save(Integer repastId, List<Ingredient> ingredients) {
        QueryWrapper<Ingredient> ingredientQueryWrapper = new QueryWrapper<>();
        ingredientQueryWrapper.eq("repast_id", repastId);
        ingredientService.remove(ingredientQueryWrapper);
        ingredientService.saveBatch(ingredients);
        return ResultVO.ok();
    }

    @ApiOperation("查看配料")
    @ApiImplicitParam(name = "repastId", value = "菜品id", required = true)
    @PostMapping("/list")
    @SaCheckPermission(value = {"chef", "admin"}, mode = SaMode.OR)
    public ResultVO detail(Integer repastId) {
        QueryWrapper<Ingredient> ingredientQueryWrapper = new QueryWrapper<>();
        ingredientQueryWrapper.eq("repast_id", repastId);
        List<Ingredient> ingredientList = ingredientService.list(ingredientQueryWrapper);
        return ResultVO.ok(ingredientList);
    }
}
