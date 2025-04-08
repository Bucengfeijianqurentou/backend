package com.gb.backend.common.enums;

/**
 * 卫生条件枚举
 */
public enum HygieneCondition {
    GOOD("良好"),
    NORMAL("一般"),
    POOR("差");

    private final String description;

    HygieneCondition(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
} 