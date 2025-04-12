package com.gb.backend.entity.dto;

import lombok.Data;
import java.util.List;

/**
 * 菜单发放的数据传输对象
 */
@Data
public class BatchDistributeDTO {
    /**
     * 菜单ID
     */
    private Integer menuId;
    
    /**
     * 发放对象列表
     */
    private List<String> recipients;
    
    /**
     * 发放人姓名
     */
    private String distributor;
} 