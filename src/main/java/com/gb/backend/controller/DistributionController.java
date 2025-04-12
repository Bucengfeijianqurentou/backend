package com.gb.backend.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gb.backend.common.Result;
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
     * 分页获取发放记录列表
     *
     * @param page 页码
     * @param size 每页记录数
     * @return 分页数据
     */
    @GetMapping
    public Result<Page<Distribution>> getDistributionList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Distribution> distributionPage = distributionService.getDistributionPage(page, size);
        return Result.success(distributionPage);
    }

    /**
     * 根据ID获取发放记录详情
     *
     * @param id 发放记录ID
     * @return 发放记录详情
     */
    @GetMapping("/{id}")
    public Result<Distribution> getDistributionById(@PathVariable Integer id) {
        Distribution distribution = distributionService.getById(id);
        if (distribution != null) {
            return Result.success(distribution);
        } else {
            return Result.error("发放记录不存在");
        }
    }

    /**
     * 创建发放记录
     *
     * @param distribution 发放记录信息
     * @return 创建结果
     */
    @PostMapping
    public Result<Boolean> createDistribution(@RequestBody Distribution distribution) {
        boolean success = distributionService.createDistribution(distribution);
        if (success) {
            return Result.success(true);
        } else {
            return Result.error("创建发放记录失败");
        }
    }

    /**
     * 更新发放记录
     *
     * @param id 发放记录ID
     * @param distribution 发放记录信息
     * @return 更新结果
     */
    @PutMapping("/{id}")
    public Result<Boolean> updateDistribution(
            @PathVariable Integer id,
            @RequestBody Distribution distribution) {
        distribution.setId(id);
        boolean success = distributionService.updateById(distribution);
        if (success) {
            return Result.success(true);
        } else {
            return Result.error("更新发放记录失败");
        }
    }

    /**
     * 删除发放记录
     *
     * @param id 发放记录ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public Result<Boolean> deleteDistribution(@PathVariable Integer id) {
        boolean success = distributionService.removeById(id);
        if (success) {
            return Result.success(true);
        } else {
            return Result.error("删除发放记录失败");
        }
    }

    /**
     * 根据时间范围查询发放记录
     *
     * @param page 页码
     * @param size 每页记录数
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 分页数据
     */
    @GetMapping("/time-range")
    public Result<Page<Distribution>> getDistributionByTimeRange(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        Page<Distribution> distributionPage = distributionService.getDistributionByTimeRange(page, size, startTime, endTime);
        return Result.success(distributionPage);
    }

    /**
     * 根据菜单ID查询发放记录
     *
     * @param menuId 菜单ID
     * @param page 页码
     * @param size 每页记录数
     * @return 分页数据
     */
    @GetMapping("/menu/{menuId}")
    public Result<Page<Distribution>> getDistributionByMenuId(
            @PathVariable Integer menuId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Distribution> distributionPage = distributionService.getDistributionByMenuId(page, size, menuId);
        return Result.success(distributionPage);
    }
}