package com.gb.backend.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gb.backend.entity.Processing;
import com.gb.backend.service.ProcessingService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

/**
 * 食品加工管理控制器
 */
@RestController
@RequestMapping("/api/processings")
@RequiredArgsConstructor
public class ProcessingController {
    
    private final ProcessingService processingService;

    /**
     * 分页查询加工记录
     */
    @GetMapping
    public Page<Processing> list(@RequestParam(defaultValue = "1") int page,
                               @RequestParam(defaultValue = "10") int size) {
        return processingService.page(new Page<>(page, size));
    }

    /**
     * 根据ID查询加工记录
     */
    @GetMapping("/{id}")
    public Processing getById(@PathVariable Integer id) {
        return processingService.getById(id);
    }

    /**
     * 根据采购ID查询加工记录
     */
    @GetMapping("/purchase/{purchaseId}")
    public Page<Processing> getByPurchaseId(
            @PathVariable Integer purchaseId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return processingService.findByPurchaseId(purchaseId, page, size);
    }

    /**
     * 根据加工人员ID查询加工记录
     */
    @GetMapping("/processor/{processorId}")
    public Page<Processing> getByProcessorId(
            @PathVariable Integer processorId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return processingService.findByProcessorId(processorId, page, size);
    }

    /**
     * 根据时间范围查询加工记录
     */
    @GetMapping("/time-range")
    public Page<Processing> getByTimeRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return processingService.findByTimeRange(startTime, endTime, page, size);
    }

    /**
     * 新增加工记录
     */
    @PostMapping
    public boolean save(@RequestBody Processing processing) {
        return processingService.save(processing);
    }

    /**
     * 更新加工记录
     */
    @PutMapping("/{id}")
    public boolean update(@PathVariable Integer id, @RequestBody Processing processing) {
        processing.setId(id);
        return processingService.updateById(processing);
    }

    /**
     * 删除加工记录
     */
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id) {
        return processingService.removeById(id);
    }
} 