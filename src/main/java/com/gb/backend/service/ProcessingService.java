package com.gb.backend.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gb.backend.entity.Processing;
import com.gb.backend.common.enums.HygieneCondition;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * 食品加工服务接口
 */
public interface ProcessingService extends IService<Processing> {
    /**
     * 根据批次号查询加工记录
     * @param batchNumber 批次号
     * @param page 页码
     * @param size 每页大小
     * @return 分页后的加工记录
     */
    Page<Processing> findByBatchNumber(String batchNumber, int page, int size);

    /**
     * 根据加工人员ID查询加工记录
     * @param processorId 加工人员ID
     * @param page 页码
     * @param size 每页大小
     * @return 分页后的加工记录
     */
    Page<Processing> findByProcessorId(Integer processorId, int page, int size);

    /**
     * 根据时间范围查询加工记录
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param page 页码
     * @param size 每页大小
     * @return 分页后的加工记录
     */
    Page<Processing> findByTimeRange(LocalDateTime startTime, LocalDateTime endTime, int page, int size);

    /**
     * 根据卫生条件查询加工记录
     * @param condition 卫生条件
     * @param page 页码
     * @param size 每页大小
     * @return 分页后的加工记录
     */
    Page<Processing> findByHygieneCondition(HygieneCondition condition, int page, int size);

    /**
     * 根据加工方法查询加工记录
     * @param method 加工方法
     * @param page 页码
     * @param size 每页大小
     * @return 分页后的加工记录
     */
    Page<Processing> findByMethod(String method, int page, int size);

    /**
     * 统计某个时间段内各卫生条件的分布
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 各卫生条件的数量统计
     */
    Map<HygieneCondition, Long> calculateHygieneConditionDistribution(LocalDateTime startTime, LocalDateTime endTime);
} 