package com.example.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.vo.ResultVO;
import com.example.domain.Admin;
import com.example.domain.Authority;
import com.example.service.AdminService;
import com.example.service.AuthorityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "管理员账户相关接口")
@RestController
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private AuthorityService authorityService;

    @ApiOperation(value = "管理员登录")
    @ApiImplicitParams({@ApiImplicitParam(name = "userName", value = "用户名", required = true), @ApiImplicitParam(name = "passWord", value = "密码", required = true)})
    @PostMapping("/admin/login")
    public ResultVO login(Admin admin) {
        if (admin == null || !StringUtils.hasText(admin.getUserName()) || !StringUtils.hasText(admin.getPassWord())) {
            return ResultVO.error("账号或密码格式错误！");
        }
        QueryWrapper<Admin> adminQueryWrapper = new QueryWrapper<>();
        adminQueryWrapper.eq("user_name", admin.getUserName());
        Admin dbAdmin = adminService.getOne(adminQueryWrapper);
        System.out.println(SaSecureUtil.md5(admin.getPassWord()));
        if (dbAdmin != null && SaSecureUtil.md5(admin.getPassWord()).equals(dbAdmin.getPassWord())) {
            StpUtil.login(dbAdmin.getId());
            return ResultVO.ok();
        } else {
            return ResultVO.error("账号或密码错误！");
        }
    }

    @ApiOperation(value = "管理员登出")
    @SaCheckLogin
    @PostMapping("/admin/logout")
    public ResultVO logout() {
        StpUtil.logout();
        return ResultVO.ok();
    }

    @ApiOperation("管理员注册")
    @ApiImplicitParams({@ApiImplicitParam(name = "userName", value = "账户名", required = true), @ApiImplicitParam(name = "nickName", value = "昵称", required = true), @ApiImplicitParam(name = "passWord", value = "密码", required = true)})
    @PostMapping("/register")
    public ResultVO register(Admin admin) {
        QueryWrapper<Admin> adminQueryWrapper = new QueryWrapper<>();
        adminQueryWrapper.eq("user_name",admin.getUserName());
        if (adminService.count(adminQueryWrapper) > 0) {
            return ResultVO.error("账户名已存在！");
        }
        admin.setPassWord(SaSecureUtil.md5(admin.getPassWord()));
        adminService.save(admin);
        Authority authority = new Authority();
        authority.setUserId(admin.getId());
        authority.setAuthority("admin");
        authorityService.save(authority);
        return ResultVO.ok();
    }

    @ApiOperation(value = "管理员修改账号信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "nickName", value = "昵称"), @ApiImplicitParam(name = "passWord", value = "密码")})
    @SaCheckLogin
    @PostMapping("/admin/update")
    public ResultVO update(Admin admin) {
        if (admin == null) {
            return ResultVO.error("非法输入！");
        }
        admin.setUserName(null);
        admin.setId(StpUtil.getLoginIdAsInt());
        if (admin.getPassWord() != null) {
            admin.setPassWord(SaSecureUtil.md5(admin.getPassWord()));
        }
        adminService.updateById(admin);
        return ResultVO.ok();
    }
}
