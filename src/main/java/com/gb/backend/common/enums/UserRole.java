package com.gb.backend.common.enums;

public enum UserRole {
    ADMIN("管理员"),
    INSPECTOR("监管人员"),
    STAFF("食堂工作人员"),
    STUDENT_PARENT("学生/家长");

    private final String description;

    UserRole(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
} 