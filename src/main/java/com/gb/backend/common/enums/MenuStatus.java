package com.gb.backend.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 菜单状态枚举
 */
public enum MenuStatus {
    UNPUBLISHED("0", "未发放"),
    PUBLISHED("1", "已发放");

    /**
     * 状态码
     */
    @EnumValue
    private final String code;

    /**
     * 状态描述
     */
    private final String description;

    MenuStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 获取状态码
     *
     * @return 状态码
     */
    @JsonValue
    public String getCode() {
        return code;
    }

    /**
     * 获取状态描述
     *
     * @return 状态描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 根据状态码获取枚举值
     *
     * @param code 状态码
     * @return 枚举值，如果不存在则返回null
     */
    @JsonCreator
    public static MenuStatus fromCode(String code) {
        for (MenuStatus status : MenuStatus.values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }
} 