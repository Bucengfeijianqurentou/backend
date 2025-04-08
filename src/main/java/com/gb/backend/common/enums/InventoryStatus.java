package com.gb.backend.common.enums;

/**
 * 库存状态枚举
 */
public enum InventoryStatus {
    UNPROCESSED("未加工"),
    PROCESSED("已加工"),
    DISTRIBUTED("已发放");

    private final String description;

    InventoryStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
} 