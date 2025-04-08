package com.gb.backend.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
     * 分页查询采购记录
     */
    @GetMapping
    public Page<Purchase> list(@RequestParam(defaultValue = "1") int page,
                             @RequestParam(defaultValue = "10") int size) {
        return purchaseService.page(new Page<>(page, size));
    }

    /**
     * 根据ID查询采购记录
     */
    @GetMapping("/{id}")
    public Purchase getById(@PathVariable Integer id) {
        return purchaseService.getById(id);
    }

    /**
     * 根据批次号查询采购记录
     */
    @GetMapping("/batch/{batchNumber}")
    public Purchase getByBatchNumber(@PathVariable String batchNumber) {
        return purchaseService.findByBatchNumber(batchNumber);
    }

    /**
     * 根据日期范围查询采购记录
     */
    @GetMapping("/date-range")
    public Page<Purchase> getByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return purchaseService.findByDateRange(startDate, endDate, page, size);
    }

    /**
     * 根据食品ID查询采购记录
     */
    @GetMapping("/food/{foodId}")
    public Page<Purchase> getByFoodId(
            @PathVariable Integer foodId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return purchaseService.findByFoodId(foodId, page, size);
    }

    /**
     * 新增采购记录
     */
    @PostMapping
    public boolean save(@RequestBody Purchase purchase) {
        return purchaseService.save(purchase);
    }

    /**
     * 更新采购记录
     */
    @PutMapping("/{id}")
    public boolean update(@PathVariable Integer id, @RequestBody Purchase purchase) {
        purchase.setId(id);
        return purchaseService.updateById(purchase);
    }

    /**
     * 删除采购记录
     */
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id) {
        return purchaseService.removeById(id);
    }
} 