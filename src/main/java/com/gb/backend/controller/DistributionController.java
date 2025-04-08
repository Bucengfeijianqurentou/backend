package com.gb.backend.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gb.backend.entity.Distribution;
import com.gb.backend.service.DistributionService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

/**
 * 食品发放管理控制器
 */
@RestController
@RequestMapping("/api/distributions")
@RequiredArgsConstructor
public class DistributionController {

    private final DistributionService distributionService;

    /**
     * 分页查询发放记录
     */
    @GetMapping
    public Page<Distribution> list(@RequestParam(defaultValue = "1") int page,
                                   @RequestParam(defaultValue = "10") int size) {
        return distributionService.page(new Page<>(page, size));
    }

    /**
     * 根据ID查询发放记录
     */
    @GetMapping("/{id}")
    public Distribution getById(@PathVariable Integer id) {
        return distributionService.getById(id);
    }

    /**
     * 根据加工记录ID查询发放记录
     */
    @GetMapping("/processing/{processingId}")
    public Page<Distribution> getByProcessingId(
            @PathVariable Integer processingId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return distributionService.findByProcessingId(processingId, page, size);
    }

    /**
     * 根据食品ID查询发放记录
     */
    @GetMapping("/food/{foodId}")
    public Page<Distribution> getByFoodId(
            @PathVariable Integer foodId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return distributionService.findByFoodId(foodId, page, size);
    }

    /**
     * 根据时间范围查询发放记录
     */
    @GetMapping("/time-range")
    public Page<Distribution> getByTimeRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return distributionService.findByTimeRange(startTime, endTime, page, size);
    }

    /**
     * 根据发放对象查询发放记录
     */
    @GetMapping("/recipient/{recipient}")
    public Page<Distribution> getByRecipient(
            @PathVariable String recipient,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return distributionService.findByRecipient(recipient, page, size);
    }

    /**
     * 统计发放总量
     */
    @GetMapping("/statistics/total-quantity")
    public Integer getTotalQuantity(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime,
            @RequestParam(required = false) Integer foodId) {
        return distributionService.calculateTotalQuantity(startTime, endTime, foodId);
    }

    /**
     * 新增发放记录
     */
    @PostMapping
    public boolean save(@RequestBody Distribution distribution) {
        return distributionService.save(distribution);
    }

    /**
     * 更新发放记录
     */
    @PutMapping("/{id}")
    public boolean update(@PathVariable Integer id, @RequestBody Distribution distribution) {
        distribution.setId(id);
        return distributionService.updateById(distribution);
    }

    /**
     * 删除发放记录
     */
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id) {
        return distributionService.removeById(id);
    }
}