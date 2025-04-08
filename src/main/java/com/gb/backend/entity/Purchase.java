package com.gb.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDate;

/**
 * 采购记录实体
 */
@Data
@TableName("purchases")
public class Purchase {
    /**
     * 采购记录ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    
    /**
     * 食品ID
     */
    private Integer foodId;

    /**
     * 供应商名称
     */
    private String supplier;

    /**
     * 批次号
     */
    private String batchNumber;

    /**
     * 采购数量
     */
    private Integer quantity;

    /**
     * 生产日期
     */
    private LocalDate productionDate;

    /**
     * 保质期（天）
     */
    private Integer shelfLife;

    /**
     * 采购日期
     */
    private LocalDate purchaseDate;

    /**
     * 采购人员
     */
    private String purchaser;

    /**
     * 采购相关图片路径
     */
    private String imagePath;
} 