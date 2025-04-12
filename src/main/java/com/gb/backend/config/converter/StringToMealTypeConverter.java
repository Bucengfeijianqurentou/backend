package com.gb.backend.config.converter;

import com.gb.backend.common.enums.MealType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * 字符串到餐次类型（MealType）的转换器
 * 允许接受中文描述值（如"早餐"）并转换为对应的枚举类型
 */
@Component
public class StringToMealTypeConverter implements Converter<String, MealType> {

    @Override
    public MealType convert(String source) {
        // 如果是空字符串，返回null
        if (source == null || source.trim().isEmpty()) {
            return null;
        }
        
        // 尝试通过描述来查找
        MealType result = MealType.fromDescription(source);
        if (result != null) {
            return result;
        }
        
        // 如果无法通过描述查找，尝试直接获取枚举值（兼容BREAKFAST这种传值）
        try {
            return MealType.valueOf(source);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("无法将 '" + source + "' 转换为餐次类型", e);
        }
    }
} 