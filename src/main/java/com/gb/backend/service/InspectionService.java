package com.gb.backend.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gb.backend.entity.Inspection;
import com.gb.backend.common.enums.InspectionResult;
import java.time.LocalDateTime;

/**
 * 监管检查服务接口
 */
public interface InspectionService extends IService<Inspection> {
    /**
     * 根据监管人员ID查询检查记录
     * @param inspectorId 监管人员ID
     * @param page 页码
     * @param size 每页大小
     * @return 分页后的检查记录
     */
    Page<Inspection> findByInspectorId(Integer inspectorId, int page, int size);

    /**
     * 根据时间范围查询检查记录
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param page 页码
     * @param size 每页大小
     * @return 分页后的检查记录
     */
    Page<Inspection> findByTimeRange(LocalDateTime startTime, LocalDateTime endTime, int page, int size);

    /**
     * 根据检查结果查询检查记录
     * @param result 检查结果
     * @param page 页码
     * @param size 每页大小
     * @return 分页后的检查记录
     */
    Page<Inspection> findByResult(InspectionResult result, int page, int size);



    /**
     * 根据菜单ID查询检查记录
     * @param menuId 菜单ID
     * @param page 页码
     * @param size 每页大小
     * @return 分页后的检查记录
     */
    Page<Inspection> findByMenuId(Integer menuId, int page, int size);

    /**
     * 统计某个时间段内的检查结果分布
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 各检查结果的数量统计
     */
    java.util.Map<InspectionResult, Long> calculateResultDistribution(LocalDateTime startTime, LocalDateTime endTime);
} 