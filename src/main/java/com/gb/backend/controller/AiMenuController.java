package com.gb.backend.controller;

import com.gb.backend.annotation.PassToken;
import com.gb.backend.common.Result;
import com.gb.backend.entity.dto.AiMenuRequestDTO;
import com.gb.backend.entity.vo.AiMenuResponseVO;
import com.gb.backend.service.AiMenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * AI菜单控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/ai-menu")
@PassToken
@RequiredArgsConstructor
public class AiMenuController {
    
    private final AiMenuService aiMenuService;
    
    /**
     * 根据用户需求生成智能菜单
     * 
     * @param request 用户需求请求
     * @return AI生成的菜单结果
     */
    @PostMapping("/generate")
    public Result<AiMenuResponseVO> generateMenu(@RequestBody AiMenuRequestDTO request) {
        try {
            log.info("收到AI菜单生成请求: {}", request.getUserQuery());
            AiMenuResponseVO response = aiMenuService.generateMenu(request);
            
            if (response.getSuccess()) {
                return Result.success(response);
            } else {
                return Result.error(response.getErrorMessage());
            }
        } catch (Exception e) {
            log.error("AI菜单生成接口异常: {}", e.getMessage(), e);
            return Result.error("AI菜单生成服务异常，请稍后重试");
        }
    }
    
    /**
     * 简单的菜单生成接口（用于快速测试）
     * 
     * @param query 用户查询
     * @return 生成的菜单内容
     */
    @GetMapping("/generate-simple")
    public Result<String> generateSimpleMenu(@RequestParam String query) {
        try {
            log.info("收到简单AI菜单生成请求: {}", query);
            String menuContent = aiMenuService.generateMenu(query);
            return Result.success(menuContent);
        } catch (Exception e) {
            log.error("简单AI菜单生成接口异常: {}", e.getMessage(), e);
            return Result.error("AI菜单生成服务异常，请稍后重试");
        }
    }
}