package com.gb.backend.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gb.backend.entity.Distribution;
import java.time.LocalDateTime;

/**
 * 食品发放服务接口
 */
public interface DistributionService extends IService<Distribution> {
    /**
     * 根据加工记录ID查询发放记录
     * @param processingId 加工记录ID
     * @param page 页码
     * @param size 每页大小
     * @return 分页后的发放记录
     */
    Page<Distribution> findByProcessingId(Integer processingId, int page, int size);

    /**
     * 根据食品ID查询发放记录
     * @param foodId 食品ID
     * @param page 页码
     * @param size 每页大小
     * @return 分页后的发放记录
     */
    Page<Distribution> findByFoodId(Integer foodId, int page, int size);

    /**
     * 根据时间范围查询发放记录
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param page 页码
     * @param size 每页大小
     * @return 分页后的发放记录
     */
    Page<Distribution> findByTimeRange(LocalDateTime startTime, LocalDateTime endTime, int page, int size);

    /**
     * 根据发放对象查询发放记录
     * @param recipient 发放对象
     * @param page 页码
     * @param size 每页大小
     * @return 分页后的发放记录
     */
    Page<Distribution> findByRecipient(String recipient, int page, int size);

    /**
     * 统计某个时间段内的发放总量
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param foodId 食品ID（可选）
     * @return 发放总量
     */
    Integer calculateTotalQuantity(LocalDateTime startTime, LocalDateTime endTime, Integer foodId);
} 