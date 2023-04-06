package com.example.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.domain.Repast;
import com.example.service.RepastService;
import com.example.common.vo.ResultVO;
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

@Api(tags = "菜品")
@SaCheckPermission("admin")
@RestController
@RequestMapping("/repast")
public class RepastController {
    @Autowired
    private RepastService repastService;

    @ApiOperation("添加菜品")
    @ApiImplicitParams({@ApiImplicitParam(name = "repastName", value = "菜品名称", required = true), @ApiImplicitParam(name = "price", value = "菜品默认价格", required = true), @ApiImplicitParam(name = "info", value = "菜品描述", required = true), @ApiImplicitParam(name = "imgUrl", value = "菜品图片地址", required = true), @ApiImplicitParam(name = "salesStatus", value = "菜品销售状态", required = true)})
    @PostMapping("/add")
    public ResultVO add(Repast repast) {
        if (repast == null || !StringUtils.hasText(repast.getRepastName()) || !StringUtils.hasText(repast.getInfo()) || !StringUtils.hasText(repast.getImgUrl()) || repast.getPrice() == null) {
            return ResultVO.error("非法输入！");
        }
        repastService.save(repast);
        return ResultVO.ok();
    }

    @ApiOperation("通过菜品id删除菜品")
    @ApiImplicitParam(name = "repastId", value = "菜品id")
    @PostMapping("/delete")
    public ResultVO deleteById(Integer repastId) {
        repastService.removeById(repastId);
        return ResultVO.ok();
    }

    @ApiOperation("通过菜品id批量删除菜品")
    @ApiImplicitParam(name = "repastIds", value = "菜品id列表")
    @PostMapping("/deleteByIds")
    public ResultVO deleteByIds(List<Integer> repastIds) {
        repastService.removeByIds(repastIds);
        return ResultVO.ok();
    }

    @ApiOperation("更改菜品")
    @ApiImplicitParams({@ApiImplicitParam(name = "repastName", value = "菜品名称"), @ApiImplicitParam(name = "price", value = "菜品默认价格"), @ApiImplicitParam(name = "info", value = "菜品描述"), @ApiImplicitParam(name = "imgUrl", value = "菜品图片地址"), @ApiImplicitParam(name = "salesStatus", value = "菜品销售状态")})
    @PostMapping("/update")
    public ResultVO updateById(Repast repast) {
        repastService.updateById(repast);
        return ResultVO.ok();
    }

    @ApiOperation("下架菜品")
    @ApiImplicitParam(name = "repastId", value = "菜品Id")
    @PostMapping("/forbid")
    public ResultVO forbidById(Integer repastId) {
        Repast repast = new Repast();
        repast.setId(repastId);
        repast.setSalesStatus(0);
        repastService.updateById(repast);
        return ResultVO.ok();
    }

    @ApiOperation("上架菜品")
    @ApiImplicitParam(name = "repastId", value = "菜品Id")
    @PostMapping("/vend")
    public ResultVO vendById(Integer repastId) {
        Repast repast = new Repast();
        repast.setId(repastId);
        repast.setSalesStatus(1);
        repastService.updateById(repast);
        return ResultVO.ok();
    }


    @ApiOperation("查询所有菜品")
    @ApiImplicitParams({@ApiImplicitParam(name = "pageNum", value = "页面号"), @ApiImplicitParam(name = "pageSize", value = "页面大小")})
    @PostMapping("/list")
    public ResultVO list(Long pageNum,Long pageSize){
        if (pageNum == null) {
            pageNum = 1L;
        }
        if (pageSize == null) {
            pageSize = 10L;
        }
        Page<Repast> repastPage = new Page<>(pageNum, pageSize);
        Page<Repast> page = repastService.page(repastPage);
        return ResultVO.ok(page);
    }
}
