package com.gb.backend.service;

import com.gb.backend.entity.dto.AiMenuRequestDTO;
import com.gb.backend.entity.vo.AiMenuResponseVO;

/**
 * AI菜单服务接口
 */
public interface AiMenuService {
    
    /**
     * 根据用户需求生成智能菜单
     * 
     * @param request 用户需求
     * @return AI生成的菜单结果
     */
    AiMenuResponseVO generateMenu(AiMenuRequestDTO request);
    
    /**
     * 根据简单查询生成菜单
     * 
     * @param userQuery 用户查询字符串
     * @return 生成的菜单内容
     */
    String generateMenu(String userQuery);
}