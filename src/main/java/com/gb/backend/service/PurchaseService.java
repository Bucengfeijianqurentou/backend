package com.gb.backend.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gb.backend.entity.Purchase;
import java.time.LocalDate;

/**
 * 采购服务接口
 */
public interface PurchaseService extends IService<Purchase> {
    /**
     * 根据批次号查询采购记录
     * @param batchNumber 批次号
     * @return 采购记录
     */
    Purchase findByBatchNumber(String batchNumber);

    /**
     * 根据日期范围查询采购记录
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param page 页码
     * @param size 每页大小
     * @return 分页后的采购记录
     */
    Page<Purchase> findByDateRange(LocalDate startDate, LocalDate endDate, int page, int size);

    /**
     * 根据食品ID查询采购记录
     * @param foodId 食品ID
     * @param page 页码
     * @param size 每页大小
     * @return 分页后的采购记录
     */
    Page<Purchase> findByFoodId(Integer foodId, int page, int size);
} 