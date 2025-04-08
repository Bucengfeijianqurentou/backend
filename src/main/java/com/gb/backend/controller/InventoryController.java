package com.gb.backend.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gb.backend.entity.Inventory;
import com.gb.backend.service.InventoryService;
import com.gb.backend.common.enums.InventoryStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 库存管理控制器
 */
@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
    
    private final InventoryService inventoryService;

    /**
     * 分页查询库存列表
     */
    @GetMapping
    public Page<Inventory> list(@RequestParam(defaultValue = "1") int page,
                               @RequestParam(defaultValue = "10") int size) {
        return inventoryService.page(new Page<>(page, size));
    }

    /**
     * 根据ID查询库存记录
     */
    @GetMapping("/{id}")
    public Inventory getById(@PathVariable Integer id) {
        return inventoryService.getById(id);
    }

    /**
     * 根据食品ID查询库存记录
     */
    @GetMapping("/food/{foodId}")
    public Page<Inventory> getByFoodId(
            @PathVariable Integer foodId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return inventoryService.findByFoodId(foodId, page, size);
    }

    /**
     * 根据批次号查询库存记录
     */
    @GetMapping("/batch/{batchNumber}")
    public Inventory getByBatchNumber(@PathVariable String batchNumber) {
        return inventoryService.findByBatchNumber(batchNumber);
    }

    /**
     * 根据库存状态查询库存记录
     */
    @GetMapping("/status/{status}")
    public Page<Inventory> getByStatus(
            @PathVariable InventoryStatus status,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return inventoryService.findByStatus(status, page, size);
    }

    /**
     * 新增库存记录
     */
    @PostMapping
    public boolean save(@RequestBody Inventory inventory) {
        return inventoryService.save(inventory);
    }

    /**
     * 更新库存记录
     */
    @PutMapping("/{id}")
    public boolean update(@PathVariable Integer id, @RequestBody Inventory inventory) {
        inventory.setId(id);
        return inventoryService.updateById(inventory);
    }

    /**
     * 更新库存数量
     */
    @PutMapping("/{id}/quantity/{quantity}")
    public boolean updateQuantity(@PathVariable Integer id, @PathVariable Integer quantity) {
        return inventoryService.updateQuantity(id, quantity);
    }

    /**
     * 更新库存状态
     */
    @PutMapping("/{id}/status/{status}")
    public boolean updateStatus(@PathVariable Integer id, @PathVariable InventoryStatus status) {
        return inventoryService.updateStatus(id, status);
    }

    /**
     * 删除库存记录
     */
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id) {
        return inventoryService.removeById(id);
    }
} 