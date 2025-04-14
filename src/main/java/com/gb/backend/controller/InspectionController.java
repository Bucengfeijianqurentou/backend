package com.gb.backend.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gb.backend.entity.Inspection;
import com.gb.backend.service.InspectionService;
import com.gb.backend.common.enums.InspectionResult;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * 监管检查管理控制器
 */
@RestController
@RequestMapping("/api/inspections")
@RequiredArgsConstructor
public class InspectionController {
    
    private final InspectionService inspectionService;

    /**
     * 分页查询检查记录
     */
    @GetMapping
    public Page<Inspection> list(@RequestParam(defaultValue = "1") int page,
                               @RequestParam(defaultValue = "10") int size) {
        return inspectionService.page(new Page<>(page, size));
    }

    /**
     * 根据ID查询检查记录
     */
    @GetMapping("/{id}")
    public Inspection getById(@PathVariable Integer id) {
        return inspectionService.getById(id);
    }

    /**
     * 根据监管人员ID查询检查记录
     */
    @GetMapping("/inspector/{inspectorId}")
    public Page<Inspection> getByInspectorId(
            @PathVariable Integer inspectorId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return inspectionService.findByInspectorId(inspectorId, page, size);
    }

    /**
     * 根据时间范围查询检查记录
     */
    @GetMapping("/time-range")
    public Page<Inspection> getByTimeRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return inspectionService.findByTimeRange(startTime, endTime, page, size);
    }

    /**
     * 根据检查结果查询检查记录
     */
    @GetMapping("/result/{result}")
    public Page<Inspection> getByResult(
            @PathVariable InspectionResult result,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return inspectionService.findByResult(result, page, size);
    }



    /**
     * 根据菜单ID查询检查记录
     */
    @GetMapping("/menu/{menuId}")
    public Page<Inspection> getByMenuId(
            @PathVariable Integer menuId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return inspectionService.findByMenuId(menuId, page, size);
    }

    /**
     * 统计检查结果分布
     */
    @GetMapping("/statistics/result-distribution")
    public Map<InspectionResult, Long> getResultDistribution(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        return inspectionService.calculateResultDistribution(startTime, endTime);
    }

    /**
     * 新增检查记录
     */
    @PostMapping
    public boolean save(@RequestBody Inspection inspection) {
        return inspectionService.save(inspection);
    }

    /**
     * 更新检查记录
     */
    @PutMapping("/{id}")
    public boolean update(@PathVariable Integer id, @RequestBody Inspection inspection) {
        inspection.setId(id);
        return inspectionService.updateById(inspection);
    }

    /**
     * 删除检查记录
     */
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id) {
        return inspectionService.removeById(id);
    }
} 