package com.gb.backend.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 餐次类型枚举
 */
public enum MealType {
    BREAKFAST("早餐"),
    LUNCH("午餐"),
    DINNER("晚餐");

    /**
     * 枚举描述
     */
    @EnumValue // 标记数据库存储的值
    @JsonValue // 标记JSON序列化时使用的值
    private final String description;

    MealType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
    
    /**
     * 根据描述获取枚举值
     *
     * @param description 枚举描述
     * @return 枚举值，如果不存在则返回null
     */
    @JsonCreator
    public static MealType fromDescription(String description) {
        for (MealType type : MealType.values()) {
            if (type.getDescription().equals(description)) {
                return type;
            }
        }
        return null;
    }
} 