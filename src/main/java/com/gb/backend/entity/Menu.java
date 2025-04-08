package com.gb.backend.entity;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gb.backend.common.enums.MealType;
import lombok.Data;
import java.time.LocalDate;

/**
 * 菜单实体
 */
@Data
@TableName("menus")
public class Menu {
    /**
     * 菜单ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    
    /**
     * 菜单日期
     */
    private LocalDate menuDate;
    
    /**
     * 餐次类型（早餐、午餐、晚餐）
     */
    @EnumValue
    private MealType mealType;
    
    /**
     * 菜品列表（JSON格式）
     */
    private String dishes;

    /**
     * 菜单图片路径
     */
    private String imagePath;
} 