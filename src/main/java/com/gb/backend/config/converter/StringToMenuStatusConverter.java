package com.gb.backend.config.converter;

import com.gb.backend.common.enums.MenuStatus;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * 字符串到菜单状态（MenuStatus）的转换器
 * 允许接受状态码（如"0"）或描述值（如"待审查"）并转换为对应的枚举类型
 */
@Component
public class StringToMenuStatusConverter implements Converter<String, MenuStatus> {

    @Override
    public MenuStatus convert(String source) {
        // 如果是空字符串，返回null
        if (source == null || source.trim().isEmpty()) {
            return null;
        }
        
        // 尝试通过状态码来查找
        MenuStatus result = MenuStatus.fromCode(source);
        if (result != null) {
            return result;
        }
        
        // 如果无法通过状态码查找，尝试直接获取枚举值（兼容PENDING这种传值）
        try {
            return MenuStatus.valueOf(source);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("无法将 '" + source + "' 转换为菜单状态类型", e);
        }
    }
} 