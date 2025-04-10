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
    public Menu getMenuByDateAndType(LocalDate date, MealType mealType) {
        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Menu::getMenuDate, date)
               .eq(Menu::getMealType, mealType);
        return getOne(wrapper);
    }

    @Override
    public Page<Menu> pageMenu(int page, int size, LocalDate startDate, LocalDate endDate, MealType mealType) {
        Page<Menu> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
        
        if (startDate != null) {
            wrapper.ge(Menu::getMenuDate, startDate);
        }
        if (endDate != null) {
            wrapper.le(Menu::getMenuDate, endDate);
        }
        if (mealType != null) {
            wrapper.eq(Menu::getMealType, mealType);
        }
        
        wrapper.orderByDesc(Menu::getMenuDate);
        return page(pageParam, wrapper);
    }

    @Override
    public List<Menu> getMenusByDateRange(LocalDate startDate, LocalDate endDate) {
        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
        wrapper.ge(Menu::getMenuDate, startDate)
               .le(Menu::getMenuDate, endDate)
               .orderByAsc(Menu::getMenuDate);
        return list(wrapper);
    }
} 