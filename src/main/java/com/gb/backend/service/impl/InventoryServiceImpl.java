package com.gb.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gb.backend.entity.Inventory;
import com.gb.backend.mapper.InventoryMapper;
import com.gb.backend.service.InventoryService;
import com.gb.backend.common.enums.InventoryStatus;
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
                        .eq(Inventory::getFoodId, foodId));
    }

    @Override
    public Inventory findByBatchNumber(String batchNumber) {
        return getOne(new LambdaQueryWrapper<Inventory>()
                .eq(Inventory::getBatchNumber, batchNumber));
    }

    @Override
    public Page<Inventory> findByStatus(InventoryStatus status, int page, int size) {
        return page(new Page<>(page, size),
                new LambdaQueryWrapper<Inventory>()
                        .eq(Inventory::getStatus, status));
    }

    @Override
    public boolean updateQuantity(Integer id, Integer quantity) {
        return update(new LambdaUpdateWrapper<Inventory>()
                .eq(Inventory::getId, id)
                .set(Inventory::getQuantity, quantity));
    }

    @Override
    public boolean updateStatus(Integer id, InventoryStatus status) {
        return update(new LambdaUpdateWrapper<Inventory>()
                .eq(Inventory::getId, id)
                .set(Inventory::getStatus, status));
    }
} 