package com.gb.backend.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gb.backend.chain.service.WeBASEService;
import com.gb.backend.common.Result;
import com.gb.backend.entity.Food;
import com.gb.backend.entity.Inventory;
import com.gb.backend.entity.Purchase;
import com.gb.backend.entity.dto.BatchNumberReqDTO;
import com.gb.backend.entity.dto.InventoryDetailDTO;
import com.gb.backend.service.FoodService;
import com.gb.backend.service.InventoryService;
import com.gb.backend.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 库存管理控制器
 */
@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
    
    private final InventoryService inventoryService;

    private final PurchaseService purchaseService;

    private final FoodService foodService;

    /**
     * 扫码入库
     * @param BatchNumberReqDTO
     * @return
     * @throws Exception
     */
    @PostMapping("/scan")
    public Result<Inventory> scanToStoreStock(@RequestBody BatchNumberReqDTO batchNumberReqDTO) throws Exception {
        //根据批次号查询采购记录
        Purchase byBatchNumber = purchaseService.findByBatchNumber(batchNumberReqDTO.getBatchNumber());
        //根据食品名称查询食品记录
        Food food = foodService.findByName(byBatchNumber.getName());
        //创建库存记录
        Inventory inventory = new Inventory();
        inventory.setFoodId(food.getId());
        inventory.setBatchNumber(byBatchNumber.getBatchNumber());
        inventory.setTotalQuantity(byBatchNumber.getQuantity());
        inventory.setRemainingQuantity(byBatchNumber.getQuantity());
        inventory.setTransactionHash(WeBASEService.generateTransactionHash());
        inventoryService.save(inventory);
        return Result.success(inventory);
    }


    /**
     * 创建库存记录
     * @param inventory 库存记录信息
     * @return 创建结果
     */
    @PostMapping
    public Result<Inventory> create(@RequestBody Inventory inventory) {
        inventoryService.save(inventory);
        return Result.success(inventory);
    }

    /**
     * 更新库存记录
     * @param id 库存记录ID
     * @param inventory 库存记录信息
     * @return 更新结果
     */
    @PutMapping("/{id}")
    public Result<Inventory> update(@PathVariable Integer id, @RequestBody Inventory inventory) {
        inventory.setId(id);
        inventoryService.updateById(inventory);
        return Result.success(inventory);
    }

    /**
     * 删除库存记录
     * @param id 库存记录ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@PathVariable Integer id) {
        return Result.success(inventoryService.removeById(id));
    }

    /**
     * 获取指定库存记录
     * @param id 库存记录ID
     * @return 库存记录信息
     */
    @GetMapping("/{id}")
    public Result<Inventory> getById(@PathVariable Integer id) {
        return Result.success(inventoryService.getById(id));
    }

    /**
     * 分页查询库存记录
     * @param page 页码
     * @param size 每页大小
     * @return 分页后的库存记录
     */
    @GetMapping
    public Result<Page<Inventory>> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return Result.success(inventoryService.page(new Page<>(page, size)));
    }

    /**
     * 根据食品ID查询库存记录
     * @param foodId 食品ID
     * @param page 页码
     * @param size 每页大小
     * @return 库存记录列表
     */
    @GetMapping("/food/{foodId}")
    public Result<Page<Inventory>> getByFoodId(
            @PathVariable Integer foodId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return Result.success(inventoryService.findByFoodId(foodId, page, size));
    }

    /**
     * 根据批次号查询库存记录
     * @param batchNumber 批次号
     * @return 库存记录信息
     */
    @GetMapping("/batch/{batchNumber}")
    public Result<Inventory> getByBatchNumber(@PathVariable String batchNumber) {
        return Result.success(inventoryService.findByBatchNumber(batchNumber));
    }

    /**
     * 更新库存剩余数量
     * @param id 库存记录ID
     * @param quantity 更新的数量
     * @return 更新结果
     */
    @PutMapping("/{id}/quantity/{quantity}")
    public Result<Boolean> updateQuantity(
            @PathVariable Integer id,
            @PathVariable Integer quantity) {
        return Result.success(inventoryService.updateRemainingQuantity(id, quantity));
    }

    /**
     * 查询库存不足的记录
     * @param threshold 阈值
     * @param page 页码
     * @param size 每页大小
     * @return 库存不足的记录列表
     */
    @GetMapping("/low-stock/{threshold}")
    public Result<Page<Inventory>> getLowStock(
            @PathVariable Integer threshold,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return Result.success(inventoryService.findLowStock(threshold, page, size));
    }

    /**
     * 获取所有可用的库存记录（剩余数量>0）
     * @return 可用库存记录列表
     */
    @GetMapping("/available")
    public Result<List<Inventory>> getAvailableInventory() {
        return Result.success(inventoryService.findAvailableInventory());
    }

    /**
     * 获取库存详情（含食品名称）
     * @param batchNumber 批次号
     * @return 库存详情
     */
    @GetMapping("/detail/{batchNumber}")
    public Result<InventoryDetailDTO> getInventoryDetail(@PathVariable String batchNumber) {
        return Result.success(inventoryService.getInventoryDetail(batchNumber));
    }
} 