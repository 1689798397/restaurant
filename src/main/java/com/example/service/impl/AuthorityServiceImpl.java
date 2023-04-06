package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.domain.Authority;
import com.example.service.AuthorityService;
import com.example.mapper.AuthorityMapper;
import org.springframework.stereotype.Service;

/**
* @author Jack
* @description 针对表【authority】的数据库操作Service实现
* @createDate 2023-04-06 09:53:24
*/
@Service
public class AuthorityServiceImpl extends ServiceImpl<AuthorityMapper, Authority>
    implements AuthorityService{

}




