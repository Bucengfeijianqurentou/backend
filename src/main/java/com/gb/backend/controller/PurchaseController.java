package com.gb.backend.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gb.backend.common.Result;
import com.gb.backend.entity.Purchase;
import com.gb.backend.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

/**
 * 采购管理控制器
 */
@RestController
@RequestMapping("/api/purchases")
@RequiredArgsConstructor
public class PurchaseController {
    
    private final PurchaseService purchaseService;

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
     * @return 分页后的采购记录
     */
    @GetMapping
    public Result<Page<Purchase>> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
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