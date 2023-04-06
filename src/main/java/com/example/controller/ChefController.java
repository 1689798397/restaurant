package com.example.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.vo.ResultVO;
import com.example.domain.Authority;
import com.example.domain.Chef;
import com.example.service.AuthorityService;
import com.example.service.ChefService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "厨师账户相关接口")
@RestController
@RequestMapping("/chef")
public class ChefController {
    @Autowired
    private ChefService chefService;
    @Autowired
    private AuthorityService authorityService;

    @ApiOperation("厨师登录")
    @ApiImplicitParams({@ApiImplicitParam(name = "userName", value = "用户名", required = true), @ApiImplicitParam(name = "passWord", value = "密码", required = true)})
    @PostMapping("/login")
    public ResultVO login(Chef chef) {
        if (chef == null || !StringUtils.hasText(chef.getUserName()) || !StringUtils.hasText(chef.getPassWord())) {
            return ResultVO.error("非法输入");
        }
        QueryWrapper<Chef> chefQueryWrapper = new QueryWrapper<>();
        chefQueryWrapper.eq("user_name", chef.getUserName());
        Chef dbChef = chefService.getOne(chefQueryWrapper);
        if (dbChef != null && dbChef.getPassWord().equals(SaSecureUtil.md5(chef.getPassWord()))) {
            StpUtil.login(dbChef.getId());
            return ResultVO.ok();
        } else {
            return ResultVO.error("账号或密码错误！");
        }
    }

    @ApiOperation(value = "厨师登出")
    @SaCheckLogin
    @PostMapping("/logout")
    public ResultVO logout() {
        StpUtil.logout();
        return ResultVO.ok();
    }

    @ApiOperation("厨师注册")
    @ApiImplicitParams({@ApiImplicitParam(name = "userName", value = "账户名", required = true), @ApiImplicitParam(name = "nickName", value = "昵称", required = true), @ApiImplicitParam(name = "passWord", value = "密码", required = true)})
    @PostMapping("/register")
    public ResultVO register(Chef chef) {
        if (chef == null || !StringUtils.hasText(chef.getUserName()) || !StringUtils.hasText(chef.getPassWord()) || !StringUtils.hasText(chef.getNickName())) {
            return ResultVO.error("非法输入！");
        }
        chefService.save(chef);
        Authority authority = new Authority();
        authority.setUserId(chef.getId());
        authority.setAuthority("chef");
        authorityService.save(authority);
        return ResultVO.ok();
    }

    @ApiOperation(value = "厨师修改账号信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "nickName", value = "昵称"), @ApiImplicitParam(name = "password", value = "密码")})
    @SaCheckLogin
    @PostMapping("/update")
    public ResultVO update(Chef chef) {
        if (chef == null) {
            return ResultVO.error("非法输入！");
        }
        chef.setUserName(null);
        chef.setId(StpUtil.getLoginIdAsInt());
        chefService.updateById(chef);
        return ResultVO.ok();
    }
}
