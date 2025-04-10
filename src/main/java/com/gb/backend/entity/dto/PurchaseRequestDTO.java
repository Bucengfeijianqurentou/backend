package com.gb.backend.entity.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PurchaseRequestDTO {

    /*Purchase相关*/
    /**
     * 食品名称
     */
    private String name;

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
     * 采购人员id
     */
    private String purchaserId;

    /**
     * 采购相关图片路径
     */
    private String imagePath;


    /**
     * 食品描述
     */
    private String description;


}
