package com.gb.backend.entity.dto;

import lombok.Data;
import java.time.LocalDate;

/**
 * AI菜单生成请求DTO
 */
@Data
public class AiMenuRequestDTO {
    
    /**
     * 用户需求描述
     * 例如："为10岁的孩子设计一份秋季午餐菜单"
     */
    private String userQuery;
    
    /**
     * 目标日期
     */
    private LocalDate targetDate;
    
    /**
     * 餐次类型
     */
    private String mealType;
    
    /**
     * 人数（可选）
     */
    private Integer peopleCount;
    
    /**
     * 特殊要求（可选）
     */
    private String specialRequirements;
}