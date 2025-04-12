package com.gb.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gb.backend.entity.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * 菜单Mapper接口
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {
    
    /**
     * 根据日期范围和餐次类型查询菜单列表
     *
     * @param page 分页参数
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param mealType 餐次类型
     * @return 菜单分页列表
     */
    Page<Menu> selectByDateRangeAndMealType(Page<Menu> page, 
                                          @Param("startDate") LocalDate startDate, 
                                          @Param("endDate") LocalDate endDate, 
                                          @Param("mealType") String mealType);
    
    /**
     * 根据日期和餐次类型查询菜单
     *
     * @param date 日期
     * @param mealType 餐次类型
     * @return 菜单列表
     */
    List<Menu> selectByDateAndMealType(@Param("date") LocalDate date, 
                                      @Param("mealType") String mealType);
    
    /**
     * 根据创建者ID查询菜单列表
     *
     * @param page 分页参数
     * @param userId 创建者ID
     * @return 菜单分页列表
     */
    Page<Menu> selectByUserId(Page<Menu> page, @Param("userId") Integer userId);
    
    /**
     * 更新菜单状态
     *
     * @param id 菜单ID
     * @param status 状态
     * @return 影响的行数
     */
    int updateStatus(@Param("id") Integer id, @Param("status") String status);
} 