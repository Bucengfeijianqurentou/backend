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
     * 创建菜单
     *
     * @param menu 菜单信息
     * @return 是否创建成功
     */
    boolean createMenu(Menu menu);
    
    /**
     * 更新菜单信息
     *
     * @param menu 菜单信息
     * @return 是否更新成功
     */
    boolean updateMenu(Menu menu);
    
    /**
     * 删除菜单
     *
     * @param id 菜单ID
     * @return 是否删除成功
     */
    boolean deleteMenu(Integer id);
    
    /**
     * 根据ID获取菜单详情
     *
     * @param id 菜单ID
     * @return 菜单详情
     */
    Menu getMenuById(Integer id);
    
    /**
     * 分页查询菜单列表
     *
     * @param page 页码
     * @param size 每页记录数
     * @return 分页菜单列表
     */
    Page<Menu> listMenus(Integer page, Integer size);
    
    /**
     * 根据日期范围和餐次类型查询菜单
     *
     * @param page 页码
     * @param size 每页记录数
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param mealType 餐次类型
     * @return 菜单分页列表
     */
    Page<Menu> listMenusByDateRangeAndMealType(Integer page, Integer size, 
                                            LocalDate startDate, 
                                            LocalDate endDate, 
                                            MealType mealType);
    
    /**
     * 根据日期和餐次类型查询菜单
     *
     * @param date 日期
     * @param mealType 餐次类型
     * @return 菜单列表
     */
    List<Menu> getMenusByDateAndMealType(LocalDate date, MealType mealType);
    
    /**
     * 根据创建者ID查询菜单
     *
     * @param page 页码
     * @param size 每页记录数
     * @param userId 创建者ID
     * @return 菜单分页列表
     */
    Page<Menu> listMenusByUserId(Integer page, Integer size, Integer userId);
    
    /**
     * 更新菜单状态
     *
     * @param id 菜单ID
     * @param status 状态
     * @return 是否更新成功
     */
    boolean updateMenuStatus(Integer id, String status);
    
    /**
     * 根据日期查询当天所有餐次的菜单
     *
     * @param date 日期
     * @return 菜单列表
     */
    List<Menu> getMenusByDate(LocalDate date);
    
} 