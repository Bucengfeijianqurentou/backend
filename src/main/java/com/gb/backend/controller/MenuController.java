package com.gb.backend.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gb.backend.common.Result;
import com.gb.backend.common.enums.MealType;
import com.gb.backend.common.enums.MenuStatus;
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
     *
     * @param menu 菜单信息
     * @return 创建结果
     */
    @PostMapping
    public Result<Boolean> createMenu(@RequestBody Menu menu) {
        boolean success = menuService.createMenu(menu);
        if (success) {
            return Result.success(true);
        } else {
            return Result.error("创建菜单失败，可能是同一天同一餐次已存在菜单");
        }
    }

    /**
     * 更新菜单
     *
     * @param id 菜单ID
     * @param menu 菜单信息
     * @return 更新结果
     */
    @PutMapping("/{id}")
    public Result<Boolean> updateMenu(@PathVariable Integer id, @RequestBody Menu menu) {
        menu.setId(id);
        boolean success = menuService.updateMenu(menu);
        if (success) {
            return Result.success(true);
        } else {
            return Result.error("更新菜单失败，菜单不存在或已有冲突");
        }
    }

    /**
     * 删除菜单
     *
     * @param id 菜单ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public Result<Boolean> deleteMenu(@PathVariable Integer id) {
        boolean success = menuService.deleteMenu(id);
        if (success) {
            return Result.success(true);
        } else {
            return Result.error("删除菜单失败，菜单不存在");
        }
    }

    /**
     * 获取菜单详情
     *
     * @param id 菜单ID
     * @return 菜单详情
     */
    @GetMapping("/{id}")
    public Result<Menu> getMenuById(@PathVariable Integer id) {
        Menu menu = menuService.getMenuById(id);
        if (menu != null) {
            return Result.success(menu);
        } else {
            return Result.error("菜单不存在");
        }
    }

    /**
     * 分页查询菜单列表
     *
     * @param page 页码，默认1
     * @param size 每页记录数，默认10
     * @return 菜单分页列表
     */
    @GetMapping
    public Result<Page<Menu>> listMenus(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        Page<Menu> menuPage = menuService.listMenus(page, size);
        return Result.success(menuPage);
    }

    /**
     * 根据日期范围和餐次类型查询菜单
     *
     * @param page 页码，默认1
     * @param size 每页记录数，默认10
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param mealType 餐次类型（可选）
     * @return 菜单分页列表
     */
    @GetMapping("/search")
    public Result<Page<Menu>> searchMenus(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) MealType mealType) {
        Page<Menu> menuPage = menuService.listMenusByDateRangeAndMealType(page, size, startDate, endDate, mealType);
        return Result.success(menuPage);
    }

    /**
     * 根据日期和餐次类型查询菜单
     *
     * @param date 日期
     * @param mealType 餐次类型（可选）
     * @return 菜单列表
     */
    @GetMapping("/date")
    public Result<List<Menu>> getMenusByDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false) MealType mealType) {
        List<Menu> menuList;
        if (mealType != null) {
            menuList = menuService.getMenusByDateAndMealType(date, mealType);
        } else {
            menuList = menuService.getMenusByDate(date);
        }
        return Result.success(menuList);
    }

    /**
     * 根据创建者ID查询菜单
     *
     * @param userId 创建者ID
     * @param page 页码，默认1
     * @param size 每页记录数，默认10
     * @return 菜单分页列表
     */
    @GetMapping("/user/{userId}")
    public Result<Page<Menu>> getMenusByUserId(
            @PathVariable Integer userId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        Page<Menu> menuPage = menuService.listMenusByUserId(page, size, userId);
        return Result.success(menuPage);
    }

    /**
     * 更新菜单状态
     *
     * @param id 菜单ID
     * @param status 状态代码 ("0"-待审查, "1"-可发放, "2"-已发放)
     * @return 更新结果
     */
    @PutMapping("/{id}/status")
    public Result<Boolean> updateMenuStatus(
            @PathVariable Integer id,
            @RequestParam String status) {
        boolean success = menuService.updateMenuStatus(id, status);
        if (success) {
            return Result.success(true);
        } else {
            return Result.error("更新菜单状态失败，菜单不存在或状态值无效");
        }
    }
} 