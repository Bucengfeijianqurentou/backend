package com.gb.backend.entity.dto;

import lombok.Data;
import java.time.LocalDate;

/**
 * 溯源查询请求DTO
 */
@Data
public class TracingQueryDTO {
    /**
     * 查询日期
     */
    private LocalDate date;
    
    /**
     * 餐次类型（早餐、午餐、晚餐）
     */
    private String mealType;
    
    /**
     * 菜品名称
     */
    private String dishName;
} 