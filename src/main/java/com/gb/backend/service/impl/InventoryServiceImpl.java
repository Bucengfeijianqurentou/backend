package com.gb.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gb.backend.entity.Inventory;
import com.gb.backend.entity.Food;
import com.gb.backend.entity.dto.InventoryDetailDTO;
import com.gb.backend.mapper.InventoryMapper;
import com.gb.backend.service.InventoryService;
import com.gb.backend.service.FoodService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 库存服务实现类
 */
@Service
public class InventoryServiceImpl extends ServiceImpl<InventoryMapper, Inventory> implements InventoryService {

    private final FoodService foodService;

    public InventoryServiceImpl(FoodService foodService) {
        this.foodService = foodService;
    }

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
    public InventoryDetailDTO getInventoryDetail(String batchNumber) {
        // 查询库存信息
        Inventory inventory = findByBatchNumber(batchNumber);
        if (inventory == null) {
            return null;
        }
        
        // 查询食品信息
        Food food = foodService.getById(inventory.getFoodId());
        
        // 组装返回对象
        InventoryDetailDTO detailDTO = new InventoryDetailDTO();
        BeanUtils.copyProperties(inventory, detailDTO);
        
        // 设置食品名称
        if (food != null) {
            detailDTO.setFoodName(food.getName());
        } else {
            detailDTO.setFoodName("未知食品");
        }
        
        return detailDTO;
    }

    @Override
    public boolean updateRemainingQuantity(Integer id, Integer quantity) {
        return update(new LambdaUpdateWrapper<Inventory>()
                .eq(Inventory::getId, id)
                .set(Inventory::getRemainingQuantity, quantity));
    }

    @Override
    public Page<Inventory> findLowStock(Integer threshold, int page, int size) {
        return page(new Page<>(page, size),
                new LambdaQueryWrapper<Inventory>()
                        .le(Inventory::getRemainingQuantity, threshold)
                        .orderByAsc(Inventory::getRemainingQuantity));
    }
    
    @Override
    public List<Inventory> findAvailableInventory() {
        return list(new LambdaQueryWrapper<Inventory>()
                .gt(Inventory::getRemainingQuantity, 0)
                .orderByDesc(Inventory::getId));
    }
} 