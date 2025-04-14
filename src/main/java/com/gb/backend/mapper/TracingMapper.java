package com.gb.backend.mapper;

import com.gb.backend.entity.Inspection;
import com.gb.backend.entity.Menu;
import com.gb.backend.entity.Processing;
import com.gb.backend.entity.Purchase;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * 溯源查询Mapper
 */
@Mapper
public interface TracingMapper {
    /**
     * 根据日期、餐次和菜品名称查询菜单
     * 
     * @param date 日期
     * @param mealType 餐次
     * @param dishName 菜品名称
     * @return 菜单
     */
    Menu findMenuByDateAndMealTypeAndDish(@Param("date") LocalDate date, 
                                         @Param("mealType") String mealType, 
                                         @Param("dishName") String dishName);
    
    /**
     * 根据ID列表查询加工记录
     * 
     * @param processIds 加工ID列表
     * @return 加工记录列表
     */
    List<Processing> findProcessingsByIds(@Param("processIds") List<Integer> processIds);
    
    /**
     * 根据批次号查询采购记录
     * 
     * @param batchNumber 批次号
     * @return 采购记录
     */
    Purchase findPurchaseByBatchNumber(@Param("batchNumber") String batchNumber);
    
    /**
     * 根据菜单ID查询检查记录
     * 
     * @param menuId 菜单ID
     * @return 检查记录
     */
    Inspection findInspectionByMenuId(@Param("menuId") Integer menuId);
} 