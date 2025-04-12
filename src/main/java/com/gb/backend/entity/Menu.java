package com.gb.backend.entity;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonValue;
import com.gb.backend.common.enums.MealType;
import com.gb.backend.common.enums.MenuStatus;
import lombok.Data;
import java.time.LocalDate;

/**
 * 菜单实体
 */
@Data
@TableName("menus")
public class Menu {
    
    /**
     * 菜单ID，主键，自动增长
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    
    /**
     * 菜单日期
     */
    private LocalDate menuDate;
    
    /**
     * 餐次类型（早餐、午餐、晚餐）
     * EnumValue注解指定数据库存储的值
     */
    @EnumValue
    private MealType mealType;
    
    /**
     * 菜品列表，字符串形式，使用分隔符分隔不同菜品
     */
    private String dishes;
    
    /**
     * 菜单创建人ID
     */
    private Integer userId;
    
    /**
     * 菜单创建人姓名
     */
    private String userRealname;
    
    /**
     * 菜单状态：0-未发放，1-已发放
     */
    private MenuStatus status;
    
    /**
     * 菜单图片路径
     */
    private String imagePath;
} 