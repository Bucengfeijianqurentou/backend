package com.gb.backend.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gb.backend.entity.Menu;
import com.gb.backend.common.enums.MealType;

import java.time.LocalDate;
import java.util.List;

/**
 * 菜单服务接口
 */
public interface MenuService extends IService<Menu> {
    
    /**
     * 根据日期和餐次类型查询菜单
     * @param date 日期
     * @param mealType 餐次类型
     * @return 菜单信息
     */
    Menu getMenuByDateAndType(LocalDate date, MealType mealType);
    
    /**
     * 分页查询菜单列表
     * @param page 页码
     * @param size 每页大小
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param mealType 餐次类型
     * @return 分页后的菜单列表
     */
    Page<Menu> pageMenu(int page, int size, LocalDate startDate, LocalDate endDate, MealType mealType);
    
    /**
     * 获取指定日期范围内的所有菜单
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 菜单列表
     */
    List<Menu> getMenusByDateRange(LocalDate startDate, LocalDate endDate);
} 