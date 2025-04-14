package com.gb.backend.entity.vo;

import com.gb.backend.entity.Inspection;
import com.gb.backend.entity.Menu;
import com.gb.backend.entity.Processing;
import com.gb.backend.entity.Purchase;
import lombok.Data;
import java.util.List;

/**
 * 溯源查询结果VO
 */
@Data
public class TracingResultVO {
    /**
     * 菜单信息
     */
    private Menu menu;
    
    /**
     * 加工记录列表
     */
    private List<Processing> processingList;
    
    /**
     * 采购记录列表（与加工记录对应）
     * 每个Processing对应一个Purchase
     */
    private List<Purchase> purchaseList;
    
    /**
     * 检查记录
     */
    private Inspection inspection;
    
    /**
     * 是否找到匹配的结果
     */
    private boolean found = false;
    
    /**
     * 错误消息
     */
    private String errorMessage;
} 