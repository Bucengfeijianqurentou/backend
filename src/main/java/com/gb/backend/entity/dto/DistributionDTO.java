package com.gb.backend.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 发放记录数据传输对象
 * 包含联表查询的菜单信息
 */
@Data
public class DistributionDTO {
    /**
     * 发放记录ID
     */
    private Integer id;
    
    /**
     * 菜单ID
     */
    private Integer menuId;
    
    /**
     * 发放时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime distributionTime;
    
    /**
     * 发放对象
     */
    private String recipient;
    
    /**
     * 发放人
     */
    private String distributor;
    
    /**
     * 菜单日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate menuDate;
    
    /**
     * 餐次类型
     */
    private String mealType;
    
    /**
     * 菜品列表
     */
    private String dishes;
    
    /**
     * 菜单状态
     */
    private String menuStatus;
    
    /**
     * 菜单图片路径
     */
    private String imagePath;
} 