package com.gb.backend.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gb.backend.annotation.DataOnChain;
import com.gb.backend.chain.service.WeBASEService;
import com.gb.backend.common.Result;
import com.gb.backend.common.enums.HygieneCondition;
import com.gb.backend.entity.Processing;
import com.gb.backend.entity.Inventory;
import com.gb.backend.service.ProcessingService;
import com.gb.backend.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

/**
 * 食品加工管理控制器
 */
@RestController
@RequestMapping("/api/processings")
@RequiredArgsConstructor
public class ProcessingController {
    
    private final ProcessingService processingService;
    private final InventoryService inventoryService;

    /**
     * 创建加工记录
     * @param processing 加工记录信息
     * @return 创建结果
     */
    @DataOnChain
    @PostMapping("/create")
    @Transactional
    public Result<Processing> create(@RequestBody Processing processing) throws Exception {
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
        
        // 验证加工人ID不为空
        if (processing.getProcessorId() == null) {
            return Result.error("加工人ID不能为空");
        }
        
        // 设置加工时间（如果为空）
        if (processing.getProcessingTime() == null) {
            processing.setProcessingTime(LocalDateTime.now());
        }
        
        // 获取对应的库存记录
        Inventory inventory = inventoryService.findByBatchNumber(processing.getBatchNumber());
        if (inventory == null) {
            return Result.error("未找到对应的库存记录");
        }
        
        // 检查库存是否足够
        if (inventory.getRemainingQuantity() < processing.getQuantity()) {
            return Result.error("库存不足，当前剩余: " + inventory.getRemainingQuantity());
        }
        
        // 保存加工记录
        processing.setTransactionHash(WeBASEService.generateTransactionHash());
        processingService.save(processing);
        
        // 更新库存数量
        int remainingQuantity = inventory.getRemainingQuantity() - processing.getQuantity();
        inventoryService.updateRemainingQuantity(inventory.getId(), remainingQuantity);
        
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

    /**
     * 获取所有加工记录（不分页）
     * @return 所有加工记录列表
     */
    @GetMapping("/all")
    public Result<List<Processing>> listAll() {
        List<Processing> processingList = processingService.list();
        return Result.success(processingList);
    }

    /**
     * 根据ID列表获取加工记录
     * @param ids ID列表，逗号分隔的字符串
     * @return 加工记录列表
     */
    @GetMapping("/ids")
    public Result<List<Processing>> getByIds(@RequestParam String ids) {
        if (ids == null || ids.trim().isEmpty()) {
            return Result.error("ID列表不能为空");
        }
        
        try {
            String[] idArray = ids.split(",");
            List<Integer> idList = new ArrayList<>();
            for (String id : idArray) {
                idList.add(Integer.parseInt(id.trim()));
            }
            
            List<Processing> processingList = processingService.listByIds(idList);
            return Result.success(processingList);
        } catch (NumberFormatException e) {
            return Result.error("ID格式不正确");
        }
    }
} 