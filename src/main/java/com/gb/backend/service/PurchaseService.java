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
     * 根据供应商名称查询采购记录
     * @param supplier 供应商名称
     * @param page 页码
     * @param size 每页大小
     * @return 分页后的采购记录
     */
    Page<Purchase> findBySupplier(String supplier, int page, int size);

    /**
     * 根据采购人员ID查询采购记录
     * @param purchaserId 采购人员ID
     * @param page 页码
     * @param size 每页大小
     * @return 分页后的采购记录
     */
    Page<Purchase> findByPurchaserId(String purchaserId, int page, int size);

    /**
     * 根据关键词搜索采购记录（食品名称或供应商）
     * @param keyword 搜索关键词
     * @param page 页码
     * @param size 每页大小
     * @return 分页后的采购记录
     */
    Page<Purchase> searchByKeyword(String keyword, int page, int size);
} 