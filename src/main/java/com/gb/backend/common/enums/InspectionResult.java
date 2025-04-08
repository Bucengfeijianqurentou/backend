package com.gb.backend.common.enums;

/**
 * 检查结果枚举
 */
public enum InspectionResult {
    PASS("合格"),
    FAIL("不合格");

    private final String description;

    InspectionResult(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
} 