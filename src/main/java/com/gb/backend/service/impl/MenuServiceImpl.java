package com.gb.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gb.backend.entity.Menu;
import com.gb.backend.mapper.MenuMapper;
import com.gb.backend.service.MenuService;
import com.gb.backend.common.enums.MealType;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * 菜单服务实现类
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
    
    @Override
    public boolean createMenu(Menu menu) {
        // 检查同一天同一餐次是否已存在菜单
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Menu::getMenuDate, menu.getMenuDate())
                   .eq(Menu::getMealType, menu.getMealType());
        
        if (count(queryWrapper) > 0) {
            // 同一天同一餐次已存在菜单
            return false;
        }
        
        return save(menu);
    }

    @Override
    public boolean updateMenu(Menu menu) {
        // 检查要更新的菜单是否存在
        Menu existingMenu = getById(menu.getId());
        if (existingMenu == null) {
            return false;
        }
        
        // 如果更改了日期或餐次，需要检查是否会与现有记录冲突
        if (!existingMenu.getMenuDate().equals(menu.getMenuDate()) || 
            !existingMenu.getMealType().equals(menu.getMealType())) {
            
            LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Menu::getMenuDate, menu.getMenuDate())
                       .eq(Menu::getMealType, menu.getMealType())
                       .ne(Menu::getId, menu.getId());
            
            if (count(queryWrapper) > 0) {
                // 已存在冲突的菜单
                return false;
            }
        }
        
        return updateById(menu);
    }

    @Override
    public boolean deleteMenu(Integer id) {
        return removeById(id);
    }

    @Override
    public Menu getMenuById(Integer id) {
        return getById(id);
    }

    @Override
    public Page<Menu> listMenus(Integer page, Integer size) {
        Page<Menu> pageParam = new Page<>(page, size);
        // 按日期降序排列，最新菜单优先显示
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Menu::getMenuDate);
        
        return page(pageParam, queryWrapper);
    }

    @Override
    public Page<Menu> listMenusByDateRangeAndMealType(Integer page, Integer size, 
                                                   LocalDate startDate, 
                                                   LocalDate endDate, 
                                                   MealType mealType) {
        Page<Menu> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        
        // 添加日期范围条件
        if (startDate != null && endDate != null) {
            queryWrapper.between(Menu::getMenuDate, startDate, endDate);
        } else if (startDate != null) {
            queryWrapper.ge(Menu::getMenuDate, startDate);
        } else if (endDate != null) {
            queryWrapper.le(Menu::getMenuDate, endDate);
        }
        
        // 添加餐次类型条件
        if (mealType != null) {
            queryWrapper.eq(Menu::getMealType, mealType);
        }
        
        queryWrapper.orderByDesc(Menu::getMenuDate);
        
        return page(pageParam, queryWrapper);
    }

    @Override
    public List<Menu> getMenusByDateAndMealType(LocalDate date, MealType mealType) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Menu::getMenuDate, date);
        
        if (mealType != null) {
            queryWrapper.eq(Menu::getMealType, mealType);
        }
        
        return list(queryWrapper);
    }

    @Override
    public Page<Menu> listMenusByUserId(Integer page, Integer size, Integer userId) {
        Page<Menu> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Menu::getUserId, userId)
                   .orderByDesc(Menu::getMenuDate);
        
        return page(pageParam, queryWrapper);
    }

    @Override
    public boolean updateMenuStatus(Integer id, String status) {
        Menu menu = getById(id);
        if (menu == null) {
            return false;
        }
        
        // 将状态字符串转换为枚举类型
        com.gb.backend.common.enums.MenuStatus menuStatus = com.gb.backend.common.enums.MenuStatus.fromCode(status);
        if (menuStatus == null) {
            return false;
        }
        
        menu.setStatus(menuStatus);
        return updateById(menu);
    }

    @Override
    public List<Menu> getMenusByDate(LocalDate date) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Menu::getMenuDate, date)
                   .orderByAsc(Menu::getMealType); // 按餐次排序：早餐、午餐、晚餐
        
        return list(queryWrapper);
    }
} 