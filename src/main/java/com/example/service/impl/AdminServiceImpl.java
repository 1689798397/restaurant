package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.domain.Admin;
import com.example.service.AdminService;
import com.example.mapper.AdminMapper;
import org.springframework.stereotype.Service;

/**
* @author Jack
* @description 针对表【admin(管理员账号信息)】的数据库操作Service实现
* @createDate 2023-04-06 09:53:21
*/
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin>
    implements AdminService{

}




