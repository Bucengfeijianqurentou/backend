package com.gb.backend.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gb.backend.common.Result;
import com.gb.backend.common.enums.HygieneCondition;
import com.gb.backend.entity.Processing;
import com.gb.backend.service.ProcessingService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * 食品加工管理控制器
 */
@RestController
@RequestMapping("/api/processings")
@RequiredArgsConstructor
public class ProcessingController {
    
    private final ProcessingService processingService;

    /**
     * 创建加工记录
     * @param processing 加工记录信息
     * @return 创建结果
     */
    @PostMapping("/create")
    @Transactional
    public Result<Processing> create(@RequestBody Processing processing) {
        // 验证加工数量不为空且大于0
        if (processing.getQuantity() == null || processing.getQuantity() <= 0) {
            return Result.error("加工数量必须大于0");
        }
        
        // 验证加工方法不为空
        if (processing.getMethod() == null || processing.getMethod().trim().isEmpty()) {
            return Result.error("加工方法不能为空");
        }
        
        // 验证批次号不为空
        if (processing.getBatchNumber() == null || processing.getBatchNumber().trim().isEmpty()) {
            return Result.error("批次号不能为空");
        }
        
        // 设置加工时间（如果为空）
        if (processing.getProcessingTime() == null) {
            processing.setProcessingTime(LocalDateTime.now());
        }
        
        // 保存加工记录
        processingService.save(processing);
        
        return Result.success(processing);
    }

    /**
     * 更新加工记录
     * @param id 加工记录ID
     * @param processing 加工记录信息
     * @return 更新结果
     */
    @PutMapping("/{id}")
    public Result<Processing> update(@PathVariable Integer id, @RequestBody Processing processing) {
        processing.setId(id);
        
        if (processingService.getById(id) == null) {
            return Result.error("加工记录不存在");
        }
        
        if (processingService.updateById(processing)) {
            return Result.success(processing);
        } else {
            return Result.error("更新失败");
        }
    }

    /**
     * 删除加工记录
     * @param id 加工记录ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@PathVariable Integer id) {
        if (processingService.getById(id) == null) {
            return Result.error("加工记录不存在");
        }
        
        boolean removed = processingService.removeById(id);
        if (removed) {
            return Result.success(true);
        } else {
            return Result.error("删除失败");
        }
    }

    /**
     * 获取指定加工记录
     * @param id 加工记录ID
     * @return 加工记录信息
     */
    @GetMapping("/{id}")
    public Result<Processing> getById(@PathVariable Integer id) {
        Processing processing = processingService.getById(id);
        if (processing != null) {
            return Result.success(processing);
        } else {
            return Result.error("加工记录不存在");
        }
    }

    /**
     * 分页查询加工记录
     * @param page 页码
     * @param size 每页大小
     * @return 分页后的加工记录
     */
    @GetMapping
    public Result<Page<Processing>> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return Result.success(processingService.page(new Page<>(page, size)));
    }

    /**
     * 根据批次号查询加工记录
     * @param batchNumber 批次号
     * @param page 页码
     * @param size 每页大小
     * @return 加工记录列表
     */
    @GetMapping("/batch/{batchNumber}")
    public Result<Page<Processing>> getByBatchNumber(
            @PathVariable String batchNumber,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return Result.success(processingService.findByBatchNumber(batchNumber, page, size));
    }

    /**
     * 根据加工人员ID查询加工记录
     * @param processorId 加工人员ID
     * @param page 页码
     * @param size 每页大小
     * @return 加工记录列表
     */
    @GetMapping("/processor/{processorId}")
    public Result<Page<Processing>> getByProcessorId(
            @PathVariable Integer processorId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return Result.success(processingService.findByProcessorId(processorId, page, size));
    }

    /**
     * 根据时间范围查询加工记录
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param page 页码
     * @param size 每页大小
     * @return 加工记录列表
     */
    @GetMapping("/time")
    public Result<Page<Processing>> getByTimeRange(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return Result.success(processingService.findByTimeRange(startTime, endTime, page, size));
    }

    /**
     * 根据卫生条件查询加工记录
     * @param condition 卫生条件
     * @param page 页码
     * @param size 每页大小
     * @return 加工记录列表
     */
    @GetMapping("/hygiene/{condition}")
    public Result<Page<Processing>> getByHygieneCondition(
            @PathVariable HygieneCondition condition,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return Result.success(processingService.findByHygieneCondition(condition, page, size));
    }

    /**
     * 根据加工方法查询加工记录
     * @param method 加工方法
     * @param page 页码
     * @param size 每页大小
     * @return 加工记录列表
     */
    @GetMapping("/method")
    public Result<Page<Processing>> getByMethod(
            @RequestParam String method,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return Result.success(processingService.findByMethod(method, page, size));
    }

    /**
     * 统计某个时间段内各卫生条件的分布
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 各卫生条件的数量统计
     */
    @GetMapping("/stats/hygiene")
    public Result<Map<HygieneCondition, Long>> getHygieneConditionDistribution(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        return Result.success(processingService.calculateHygieneConditionDistribution(startTime, endTime));
    }
} 