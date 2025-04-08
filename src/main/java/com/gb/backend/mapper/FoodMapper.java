package com.gb.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gb.backend.entity.Food;
import org.apache.ibatis.annotations.Mapper;

/**
 * 食品数据访问层
 */
@Mapper
public interface FoodMapper extends BaseMapper<Food> {
} 