package com.gb.backend.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gb.backend.entity.Processing;
import java.time.LocalDateTime;

/**
 * 食品加工服务接口
 */
public interface ProcessingService extends IService<Processing> {
    /**
     * 根据采购ID查询加工记录
     * @param purchaseId 采购ID
     * @param page 页码
     * @param size 每页大小
     * @return 分页后的加工记录
     */
    Page<Processing> findByPurchaseId(Integer purchaseId, int page, int size);

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
} 