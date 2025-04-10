package com.gb.backend.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gb.backend.common.enums.MealType;
import com.gb.backend.entity.Menu;
import com.gb.backend.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * 菜单控制器
 */
@RestController
@RequestMapping("/api/menu")
@RequiredArgsConstructor
public class MenuController {
    
    private final MenuService menuService;

    /**
     * 创建菜单
     * @param menu 菜单信息
     * @return 创建后的菜单
     */
    @PostMapping
    public Menu createMenu(@RequestBody Menu menu) {
        menuService.save(menu);
        return menu;
    }

    /**
     * 更新菜单
     * @param id 菜单ID
     * @param menu 菜单信息
     * @return 更新后的菜单
     */
    @PutMapping("/{id}")
    public Menu updateMenu(@PathVariable Integer id, @RequestBody Menu menu) {
        menu.setId(id);
        menuService.updateById(menu);
        return menu;
    }

    /**
     * 删除菜单
     * @param id 菜单ID
     * @return 是否删除成功
     */
    @DeleteMapping("/{id}")
    public boolean deleteMenu(@PathVariable Integer id) {
        return menuService.removeById(id);
    }

    /**
     * 获取指定菜单
     * @param id 菜单ID
     * @return 菜单信息
     */
    @GetMapping("/{id}")
    public Menu getMenu(@PathVariable Integer id) {
        return menuService.getById(id);
    }

    /**
     * 根据日期和餐次类型查询菜单
     * @param date 日期
     * @param mealType 餐次类型
     * @return 菜单信息
     */
    @GetMapping("/by-date-type")
    public Menu getMenuByDateAndType(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
            @RequestParam MealType mealType) {
        return menuService.getMenuByDateAndType(date, mealType);
    }

    /**
     * 分页查询菜单列表
     * @param page 页码
     * @param size 每页大小
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param mealType 餐次类型
     * @return 分页后的菜单列表
     */
    @GetMapping("/page")
    public Page<Menu> pageMenu(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            @RequestParam(required = false) MealType mealType) {
        return menuService.pageMenu(page, size, startDate, endDate, mealType);
    }

    /**
     * 获取指定日期范围内的所有菜单
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 菜单列表
     */
    @GetMapping("/date-range")
    public List<Menu> getMenusByDateRange(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        return menuService.getMenusByDateRange(startDate, endDate);
    }
} 