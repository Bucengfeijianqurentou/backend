package com.gb.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gb.backend.entity.Food;
import com.gb.backend.mapper.FoodMapper;
import com.gb.backend.service.FoodService;
import org.springframework.stereotype.Service;

/**
 * 食品服务实现类
 */
@Service
public class FoodServiceImpl extends ServiceImpl<FoodMapper, Food> implements FoodService {
    
    @Override
    public Food findByName(String name) {
        return getOne(new LambdaQueryWrapper<Food>()
                .eq(Food::getName, name)
                .last("LIMIT 1"));
    }
} 