package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.domain.COrder;
import com.example.service.COrderService;
import com.example.mapper.COrderMapper;
import org.springframework.stereotype.Service;

/**
* @author Jack
* @description 针对表【c_order(订单)】的数据库操作Service实现
* @createDate 2023-04-06 20:38:33
*/
@Service
public class COrderServiceImpl extends ServiceImpl<COrderMapper, COrder>
    implements COrderService{

}




