package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.domain.Repast;
import com.example.service.RepastService;
import com.example.mapper.RepastMapper;
import org.springframework.stereotype.Service;

/**
* @author Jack
* @description 针对表【repast(菜品)】的数据库操作Service实现
* @createDate 2023-04-06 09:53:35
*/
@Service
public class RepastServiceImpl extends ServiceImpl<RepastMapper, Repast>
    implements RepastService{

}




