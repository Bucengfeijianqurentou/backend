package com.gb.backend.entity.vo;

import lombok.Data;
import java.util.List;

/**
 * AI菜单生成响应VO
 */
@Data
public class AiMenuResponseVO {
    
    /**
     * 生成的菜单内容
     */
    private String menuContent;
    
    /**
     * 推荐菜品列表
     */
    private List<String> recommendedDishes;
    
    /**
     * 营养建议
     */
    private String nutritionAdvice;
    
    /**
     * 生成时间戳
     */
    private Long timestamp;
    
    /**
     * 是否成功生成
     */
    private Boolean success;
    
    /**
     * 错误信息（如果有）
     */
    private String errorMessage;
}