package com.gb.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gb.backend.entity.Food;

/**
 * 食品服务接口
 */
public interface FoodService extends IService<Food> {
    /**
     * 根据食品名称查询食品信息
     * @param name 食品名称
     * @return 食品信息
     */
    Food findByName(String name);
} 