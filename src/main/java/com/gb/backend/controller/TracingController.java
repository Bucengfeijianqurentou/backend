package com.gb.backend.controller;

import com.gb.backend.common.Result;
import com.gb.backend.common.enums.MealType;
import com.gb.backend.entity.dto.TracingQueryDTO;
import com.gb.backend.entity.vo.TracingResultVO;
import com.gb.backend.service.TracingService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Collections;
import com.gb.backend.entity.Menu;
import com.gb.backend.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 溯源查询控制器
 */
@RestController
@RequestMapping("/api/tracing")
@Slf4j
public class TracingController {

    @Autowired
    private TracingService tracingService;
    
    @Autowired
    private MenuService menuService;

    /**
     * 执行溯源查询
     *
     * @param queryDTO 查询参数
     * @return 溯源查询结果
     */
    @PostMapping("/query")
    public Result<TracingResultVO> query(@RequestBody TracingQueryDTO queryDTO) {
        log.info("溯源查询请求: {}", queryDTO);
        
        if (queryDTO.getDate() == null || queryDTO.getMealType() == null || queryDTO.getDishName() == null) {
            return Result.error("查询参数不完整，请提供日期、餐次和菜品名称");
        }
        
        TracingResultVO resultVO = tracingService.tracingQuery(queryDTO);
        
        if (!resultVO.isFound()) {
            return Result.error(resultVO.getErrorMessage());
        }
        
        return Result.success(resultVO);
    }
    
    /**
     * 根据日期和餐次获取菜品列表
     *
     * @param date 日期
     * @param mealType 餐次类型
     * @return 菜品列表
     */
    @GetMapping("/dishes")
    public Result<List<String>> getDishes(
            @RequestParam String date,
            @RequestParam String mealType) {
        try {
            LocalDate menuDate = LocalDate.parse(date);
            MealType mealTypeEnum;
            
            try {
                mealTypeEnum = MealType.fromDescription(mealType);
            } catch (IllegalArgumentException e) {
                return Result.error("无效的餐次类型：" + mealType);
            }
            
            List<Menu> menus = menuService.getMenusByDateAndMealType(menuDate, mealTypeEnum);
            
            if (menus.isEmpty()) {
                return Result.success(Collections.emptyList());
            }
            
            // 收集所有菜单的菜品
            List<String> dishList = new ArrayList<>();
            for (Menu menu : menus) {
                String dishes = menu.getDishes();
                if (dishes != null && !dishes.trim().isEmpty()) {
                    dishList.add(dishes.trim());
                }
            }
            
            return Result.success(dishList);
        } catch (Exception e) {
            log.error("获取菜品列表失败", e);
            return Result.error("获取菜品列表失败：" + e.getMessage());
        }
    }
}