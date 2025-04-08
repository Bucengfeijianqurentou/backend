package com.gb.backend.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gb.backend.entity.Inventory;
import com.gb.backend.common.enums.InventoryStatus;

/**
 * 库存服务接口
 */
public interface InventoryService extends IService<Inventory> {
    /**
     * 根据食品ID查询库存记录
     * @param foodId 食品ID
     * @param page 页码
     * @param size 每页大小
     * @return 分页后的库存记录
     */
    Page<Inventory> findByFoodId(Integer foodId, int page, int size);

    /**
     * 根据批次号查询库存记录
     * @param batchNumber 批次号
     * @return 库存记录
     */
    Inventory findByBatchNumber(String batchNumber);

    /**
     * 根据库存状态查询库存记录
     * @param status 库存状态
     * @param page 页码
     * @param size 每页大小
     * @return 分页后的库存记录
     */
    Page<Inventory> findByStatus(InventoryStatus status, int page, int size);

    /**
     * 更新库存数量
     * @param id 库存ID
     * @param quantity 更新后的数量
     * @return 是否更新成功
     */
    boolean updateQuantity(Integer id, Integer quantity);

    /**
     * 更新库存状态
     * @param id 库存ID
     * @param status 更新后的状态
     * @return 是否更新成功
     */
    boolean updateStatus(Integer id, InventoryStatus status);
} 