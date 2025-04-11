package com.gb.backend.entity.dto;

import lombok.Data;

/**
 * 库存详情DTO，包含食品信息
 */
@Data
public class InventoryDetailDTO {
    /**
     * 库存ID
     */
    private Integer id;
    
    /**
     * 食品ID
     */
    private Integer foodId;
    
    /**
     * 食品名称
     */
    private String foodName;
    
    /**
     * 批次号
     */
    private String batchNumber;
    
    /**
     * 总库存数量
     */
    private Integer totalQuantity;
    
    /**
     * 当前库存数量（剩余数量）
     */
    private Integer remainingQuantity;
} 