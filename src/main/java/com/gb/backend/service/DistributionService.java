package com.gb.backend.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gb.backend.entity.Distribution;
import com.gb.backend.entity.dto.DistributionDTO;
import java.time.LocalDateTime;

/**
 * 食品发放服务接口
 */
public interface DistributionService extends IService<Distribution> {
    
    /**
     * 分页查询发放记录
     *
     * @param page 页码
     * @param size 每页大小
     * @return 分页结果
     */
    Page<Distribution> getDistributionPage(int page, int size);
    
    /**
     * 根据时间范围查询发放记录
     *
     * @param page 页码
     * @param size 每页大小
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 分页结果
     */
    Page<Distribution> getDistributionByTimeRange(int page, int size, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 根据菜单ID查询发放记录
     *
     * @param page 页码
     * @param size 每页大小
     * @param menuId 菜单ID
     * @return 分页结果
     */
    Page<Distribution> getDistributionByMenuId(int page, int size, Integer menuId);
    
    /**
     * 创建发放记录
     *
     * @param distribution 发放记录实体
     * @return 是否成功
     */
    boolean createDistribution(Distribution distribution);
    
    /**
     * 分页查询发放记录及其关联的菜单信息
     *
     * @param page 页码
     * @param size 每页大小
     * @param startTime 开始时间（可选）
     * @param endTime 结束时间（可选）
     * @param menuId 菜单ID（可选）
     * @return 发放记录及菜单信息
     */
    Page<DistributionDTO> getDistributionWithMenu(int page, int size, LocalDateTime startTime, LocalDateTime endTime, Integer menuId);
    
    /**
     * 根据ID查询发放记录及其关联的菜单信息
     *
     * @param id 发放记录ID
     * @return 发放记录及菜单信息
     */
    DistributionDTO getDistributionWithMenuById(Integer id);
} 