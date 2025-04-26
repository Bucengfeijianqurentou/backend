package com.gb.backend.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gb.backend.annotation.PassToken;
import com.gb.backend.common.Result;
import com.gb.backend.entity.Evaluation;
import com.gb.backend.service.EvaluationService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 菜品评价管理控制器
 */
@RestController
@RequestMapping("/api/evaluations")
@RequiredArgsConstructor
public class EvaluationController {

    private final EvaluationService evaluationService;

    /**
     * 分页查询评价记录
     */
    @GetMapping
    public Result<Page<Evaluation>> list(@RequestParam(defaultValue = "1") int page,
                                      @RequestParam(defaultValue = "10") int size) {
        return Result.success(evaluationService.page(new Page<>(page, size)));
    }

    /**
     * 根据ID查询评价记录
     */
    @GetMapping("/{id}")
    public Result<Evaluation> getById(@PathVariable Integer id) {
        return Result.success(evaluationService.getById(id));
    }

    /**
     * 根据用户ID查询评价记录
     */
    @GetMapping("/user/{userId}")
    public Result<Page<Evaluation>> getByUserId(
            @PathVariable Integer userId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return Result.success(evaluationService.findByUserId(userId, page, size));
    }

    /**
     * 根据菜单ID查询评价记录
     */
    @GetMapping("/menu/{menuId}")
    public Result<Page<Evaluation>> getByMenuId(
            @PathVariable Integer menuId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return Result.success(evaluationService.findByMenuId(menuId, page, size));
    }

    /**
     * 根据时间范围查询评价记录
     */
    @GetMapping("/time-range")
    public Result<Page<Evaluation>> getByTimeRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return Result.success(evaluationService.findByTimeRange(startTime, endTime, page, size));
    }

    /**
     * 根据评分范围查询评价记录
     */
    @GetMapping("/rating-range")
    public Result<Page<Evaluation>> getByRatingRange(
            @RequestParam Integer minRating,
            @RequestParam Integer maxRating,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return Result.success(evaluationService.findByRatingRange(minRating, maxRating, page, size));
    }

    /**
     * 统计评分分布
     */
    @GetMapping("/statistics/rating-distribution")
    public Result<Map<Integer, Long>> getRatingDistribution(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        return Result.success(evaluationService.calculateRatingDistribution(startTime, endTime));
    }

    /**
     * 获取菜单平均评分
     */
    @GetMapping("/statistics/average-rating/menu/{menuId}")
    public Result<Double> getAverageRatingByMenu(@PathVariable Integer menuId) {
        return Result.success(evaluationService.calculateAverageRatingByMenu(menuId));
    }

    /**
     * 获取时间段平均评分
     */
    @GetMapping("/statistics/average-rating")
    public Result<Double> getAverageRating(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        return Result.success(evaluationService.calculateAverageRating(startTime, endTime));
    }

    /**
     * 新增评价记录
     */
    @PostMapping
    public Result<Boolean> save(@RequestBody Evaluation evaluation) {
        // 设置提交时间为当前时间
        if (evaluation.getSubmitTime() == null) {
            evaluation.setSubmitTime(LocalDateTime.now());
        }
        return Result.success(evaluationService.save(evaluation));
    }

    /**
     * 更新评价记录
     */
    @PutMapping("/{id}")
    public Result<Boolean> update(@PathVariable Integer id, @RequestBody Evaluation evaluation) {
        evaluation.setId(id);
        return Result.success(evaluationService.updateById(evaluation));
    }

    /**
     * 删除评价记录
     */
    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@PathVariable Integer id) {
        return Result.success(evaluationService.removeById(id));
    }
} 