package com.gb.backend.entity;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gb.backend.common.enums.InventoryStatus;
import lombok.Data;

/**
 * 库存实体
 */
@Data
@TableName("inventory")
public class Inventory {
    /**
     * 库存记录ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 食品ID
     */
    private Integer foodId;

    /**
     * 批次号
     */
    private String batchNumber;

    /**
     * 当前库存数量
     */
    private Integer quantity;

    /**
     * 库存状态（未加工、已加工、已发放）
     */
    @EnumValue
    private InventoryStatus status;
} 