package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.domain.Chef;
import com.example.service.ChefService;
import com.example.mapper.ChefMapper;
import org.springframework.stereotype.Service;

/**
* @author Jack
* @description 针对表【chef(厨师账户信息)】的数据库操作Service实现
* @createDate 2023-04-06 09:53:27
*/
@Service
public class ChefServiceImpl extends ServiceImpl<ChefMapper, Chef>
    implements ChefService{

}




