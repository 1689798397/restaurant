package com.example.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.vo.ResultVO;
import com.example.domain.Authority;
import com.example.domain.Student;
import com.example.service.AuthorityService;
import com.example.service.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@Api(tags = "学生账户相关接口")
@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private AuthorityService authorityService;

    @ApiOperation("学生登录")
    @ApiImplicitParams({@ApiImplicitParam(name = "userName", value = "用户名", required = true), @ApiImplicitParam(name = "passWord", value = "密码", required = true)})
    @PostMapping("/login")
    public ResultVO login(Student student) {
        if (student == null || !StringUtils.hasText(student.getUserName()) || !StringUtils.hasText(student.getPassWord())) {
            return ResultVO.error("非法输入！");
        }
        QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();
        studentQueryWrapper.eq("user_name", student.getUserName());
        Student dbStudent = studentService.getOne(studentQueryWrapper);
        if (dbStudent != null && SaSecureUtil.md5(student.getPassWord()).equals(dbStudent.getPassWord())) {
            StpUtil.login(dbStudent.getId());
            return ResultVO.ok();
        } else {
            return ResultVO.error("账号或密码错误！");
        }
    }


    @ApiOperation(value = "学生登出")
    @SaCheckLogin
    @PostMapping("/logout")
    public ResultVO logout() {
        StpUtil.logout();
        return ResultVO.ok();
    }

    @ApiOperation("学生注册")
    @ApiImplicitParams({@ApiImplicitParam(name = "userName", value = "账户名", required = true), @ApiImplicitParam(name = "nickName", value = "昵称", required = true), @ApiImplicitParam(name = "passWord", value = "密码", required = true)})
    @PostMapping("/register")
    public ResultVO register(Student student) {
        QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();
        studentQueryWrapper.eq("user_name",student.getUserName());
        if (studentService.count(studentQueryWrapper) > 0) {
            return ResultVO.error("账户名已存在！");
        }
        student.setPassWord(SaSecureUtil.md5(student.getPassWord()));
        studentService.save(student);
        Authority authority = new Authority();
        authority.setUserId(student.getId());
        authority.setAuthority("student");
        authorityService.save(authority);
        return ResultVO.ok();
    }
    @ApiOperation(value = "学生充值")
    @ApiImplicitParams({@ApiImplicitParam(name = "userId", value = "用户id",required = true), @ApiImplicitParam(name = "rechargeAmount", value = "充值金额",required = true)})
    @PostMapping("/recharge")
    public ResultVO recharge(Integer userId, BigDecimal rechargeAmount){
        Student student = studentService.getById(userId);
        student.setBalance(student.getBalance().add(rechargeAmount));
        studentService.updateById(student);
        return ResultVO.ok();
    }

    @ApiOperation(value = "学生修改账号信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "nickName", value = "昵称"), @ApiImplicitParam(name = "passWord", value = "密码")})
    @SaCheckLogin
    @PostMapping("/update")
    public ResultVO update(Student student) {
        if (student == null) {
            return ResultVO.error("非法输入！");
        }
        student.setUserName(null);
        student.setBalance(null);
        student.setId(StpUtil.getLoginIdAsInt());
        if (student.getPassWord() != null) {
            student.setPassWord(SaSecureUtil.md5(student.getPassWord()));
        }
        studentService.updateById(student);
        return ResultVO.ok();
    }
}
