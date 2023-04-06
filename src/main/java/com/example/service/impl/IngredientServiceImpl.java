package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.domain.Ingredient;
import com.example.service.IngredientService;
import com.example.mapper.IngredientMapper;
import org.springframework.stereotype.Service;

/**
* @author Jack
* @description 针对表【ingredient(菜品)】的数据库操作Service实现
* @createDate 2023-04-06 09:53:30
*/
@Service
public class IngredientServiceImpl extends ServiceImpl<IngredientMapper, Ingredient>
    implements IngredientService{

}




