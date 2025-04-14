package com.gb.backend.entity.vo;

import com.gb.backend.entity.Inspection;
import com.gb.backend.entity.Menu;
import com.gb.backend.entity.Processing;
import com.gb.backend.entity.Purchase;
import com.gb.backend.entity.User;
import lombok.Data;
import java.util.List;
import java.util.Map;

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
     * 菜单创建人联系方式
     */
    private String menuCreatorContact;
    
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
     * 采购人员信息Map，key为采购人员ID，value为用户信息
     */
    private Map<String, User> purchaserInfoMap;
    
    /**
     * 检查记录
     */
    private Inspection inspection;
    
    /**
     * 检查人员信息
     */
    private User inspectorInfo;
    
    /**
     * 是否找到匹配的结果
     */
    private boolean found = false;
    
    /**
     * 错误消息
     */
    private String errorMessage;
} 