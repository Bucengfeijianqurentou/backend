package com.gb.backend.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gb.backend.chain.service.WeBASEService;
import com.gb.backend.common.Result;
import com.gb.backend.entity.Food;
import com.gb.backend.entity.Inventory;
import com.gb.backend.entity.Purchase;
import com.gb.backend.entity.dto.PurchaseRequestDTO;
import com.gb.backend.service.FoodService;
import com.gb.backend.service.InventoryService;
import com.gb.backend.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 采购管理控制器
 */
@RestController
@RequestMapping("/api/purchases")
@RequiredArgsConstructor
public class PurchaseController {
    
    private final PurchaseService purchaseService;
    private final FoodService foodService;
    private final InventoryService inventoryService;

    /**
     * 创建采购记录（同时创建食品和库存记录）
     * @param purchaseRequestDTO 采购请求DTO
     * @return 创建结果
     */
    @PostMapping("/create")
    @Transactional
    public Result<Purchase> createWithFoodAndInventory(@RequestBody PurchaseRequestDTO purchaseRequestDTO) throws Exception {
        Integer foodId;
        System.out.println(purchaseRequestDTO);
        // 1. 创建新食品记录
        Food food = new Food();
        food.setName(purchaseRequestDTO.getName());
        food.setDescription(purchaseRequestDTO.getDescription());
        foodService.save(food);
        foodId = food.getId();
        
        // 2. 创建采购记录
        Purchase purchase = new Purchase();
        BeanUtils.copyProperties(purchaseRequestDTO, purchase);
        purchase.setPurchaserId(purchaseRequestDTO.getPurchaserId());
        purchase.setTransactionHash(WeBASEService.generateTransactionHash());
        purchaseService.save(purchase);
        
        // 3. 创建库存记录
        Inventory inventory = new Inventory();
        inventory.setFoodId(foodId);
        inventory.setBatchNumber(purchaseRequestDTO.getBatchNumber());
        inventory.setTotalQuantity(purchaseRequestDTO.getQuantity());
        inventory.setRemainingQuantity(purchaseRequestDTO.getQuantity());
        
        inventoryService.save(inventory);
        
        return Result.success(purchase);
    }

    /**
     * 创建采购记录
     * @param purchase 采购记录信息
     * @return 创建结果
     */
    @PostMapping
    public Result<Purchase> create(@RequestBody Purchase purchase) {
        purchaseService.save(purchase);
        return Result.success(purchase);
    }

    /**
     * 更新采购记录
     * @param id 采购记录ID
     * @param purchase 采购记录信息
     * @return 更新结果
     */
    @PutMapping("/{id}")
    public Result<Purchase> update(@PathVariable Integer id, @RequestBody Purchase purchase) {
        purchase.setId(id);
        purchaseService.updateById(purchase);
        return Result.success(purchase);
    }

    /**
     * 删除采购记录
     * @param id 采购记录ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@PathVariable Integer id) {
        return Result.success(purchaseService.removeById(id));
    }

    /**
     * 获取指定采购记录
     * @param id 采购记录ID
     * @return 采购记录信息
     */
    @GetMapping("/{id}")
    public Result<Purchase> getById(@PathVariable Integer id) {
        return Result.success(purchaseService.getById(id));
    }

    /**
     * 分页查询采购记录
     * @param page 页码
     * @param size 每页大小
     * @param keyword 搜索关键词（可选）
     * @return 分页后的采购记录
     */
    @GetMapping
    public Result<Page<Purchase>> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword) {
        if (keyword != null && !keyword.isEmpty()) {
            // 如果关键词是批次号格式
            if (keyword.startsWith("PO-")) {
                Purchase purchase = purchaseService.findByBatchNumber(keyword);
                if (purchase != null) {
                    Page<Purchase> result = new Page<>(page, size);
                    List<Purchase> records = new ArrayList<>();
                    records.add(purchase);
                    result.setRecords(records);
                    result.setTotal(1);
                    return Result.success(result);
                } else {
                    return Result.success(new Page<>(page, size));
                }
            }
            
            // 否则按名称或供应商搜索
            return Result.success(purchaseService.searchByKeyword(keyword, page, size));
        }
        return Result.success(purchaseService.page(new Page<>(page, size)));
    }

    /**
     * 根据批次号查询采购记录
     * @param batchNumber 批次号
     * @return 采购记录信息
     */
    @GetMapping("/batch/{batchNumber}")
    public Result<Purchase> getByBatchNumber(@PathVariable String batchNumber) {
        return Result.success(purchaseService.findByBatchNumber(batchNumber));
    }

    /**
     * 根据日期范围查询采购记录
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param page 页码
     * @param size 每页大小
     * @return 分页后的采购记录
     */
    @GetMapping("/date")
    public Result<Page<Purchase>> getByDateRange(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return Result.success(purchaseService.findByDateRange(startDate, endDate, page, size));
    }

    /**
     * 根据供应商查询采购记录
     * @param supplier 供应商名称
     * @param page 页码
     * @param size 每页大小
     * @return 分页后的采购记录
     */
    @GetMapping("/supplier/{supplier}")
    public Result<Page<Purchase>> getBySupplier(
            @PathVariable String supplier,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return Result.success(purchaseService.findBySupplier(supplier, page, size));
    }

    /**
     * 根据采购人员ID查询采购记录
     * @param purchaserId 采购人员ID
     * @param page 页码
     * @param size 每页大小
     * @return 分页后的采购记录
     */
    @GetMapping("/purchaser/{purchaserId}")
    public Result<Page<Purchase>> getByPurchaserId(
            @PathVariable String purchaserId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return Result.success(purchaseService.findByPurchaserId(purchaserId, page, size));
    }
} 