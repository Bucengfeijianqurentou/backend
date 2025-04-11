package com.gb.backend.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gb.backend.entity.Inventory;
import com.gb.backend.common.enums.InventoryStatus;
import com.gb.backend.entity.dto.InventoryDetailDTO;

import java.util.List;

/**
 * 库存服务接口
 */
public interface InventoryService extends IService<Inventory> {
    /**
     * 根据食品ID查询库存记录
     * @param foodId 食品ID
     * @return 库存记录列表
     */
    Page<Inventory> findByFoodId(Integer foodId, int page, int size);

    /**
     * 根据批次号查询库存记录
     * @param batchNumber 批次号
     * @return 库存记录
     */
    Inventory findByBatchNumber(String batchNumber);
    
    /**
     * 获取库存详情（包含食品信息）
     * @param batchNumber 批次号
     * @return 库存详情DTO
     */
    InventoryDetailDTO getInventoryDetail(String batchNumber);

    /**
     * 更新库存剩余数量
     * @param id 库存记录ID
     * @param quantity 更新的数量
     * @return 是否更新成功
     */
    boolean updateRemainingQuantity(Integer id, Integer quantity);

    /**
     * 查询库存不足的记录（剩余数量小于指定值）
     * @param threshold 阈值
     * @return 库存不足的记录列表
     */
    Page<Inventory> findLowStock(Integer threshold, int page, int size);
    
    /**
     * 查询所有可用库存记录（剩余数量>0）
     * @return 可用库存记录列表
     */
    List<Inventory> findAvailableInventory();
} 