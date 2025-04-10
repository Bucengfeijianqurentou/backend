package com.gb.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gb.backend.entity.Inventory;
import com.gb.backend.mapper.InventoryMapper;
import com.gb.backend.service.InventoryService;
import org.springframework.stereotype.Service;

/**
 * 库存服务实现类
 */
@Service
public class InventoryServiceImpl extends ServiceImpl<InventoryMapper, Inventory> implements InventoryService {

    @Override
    public Page<Inventory> findByFoodId(Integer foodId, int page, int size) {
        return page(new Page<>(page, size),
                new LambdaQueryWrapper<Inventory>()
                        .eq(Inventory::getFoodId, foodId)
                        .orderByDesc(Inventory::getId));
    }

    @Override
    public Inventory findByBatchNumber(String batchNumber) {
        return getOne(new LambdaQueryWrapper<Inventory>()
                .eq(Inventory::getBatchNumber, batchNumber));
    }

    @Override
    public boolean updateRemainingQuantity(Integer id, Integer quantity) {
        return update(new LambdaUpdateWrapper<Inventory>()
                .eq(Inventory::getId, id)
                .set(Inventory::getRemaining_quantity, quantity));
    }

    @Override
    public Page<Inventory> findLowStock(Integer threshold, int page, int size) {
        return page(new Page<>(page, size),
                new LambdaQueryWrapper<Inventory>()
                        .le(Inventory::getRemaining_quantity, threshold)
                        .orderByAsc(Inventory::getRemaining_quantity));
    }
} 