package com.gb.backend.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gb.backend.entity.Feedback;
import com.gb.backend.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.Map;

/**
 * 用户反馈管理控制器
 */
@RestController
@RequestMapping("/api/feedbacks")
@RequiredArgsConstructor
public class FeedbackController {
    
    private final FeedbackService feedbackService;

    /**
     * 分页查询反馈记录
     */
    @GetMapping
    public Page<Feedback> list(@RequestParam(defaultValue = "1") int page,
                             @RequestParam(defaultValue = "10") int size) {
        return feedbackService.page(new Page<>(page, size));
    }

    /**
     * 根据ID查询反馈记录
     */
    @GetMapping("/{id}")
    public Feedback getById(@PathVariable Integer id) {
        return feedbackService.getById(id);
    }

    /**
     * 根据用户ID查询反馈记录
     */
    @GetMapping("/user/{userId}")
    public Page<Feedback> getByUserId(
            @PathVariable Integer userId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return feedbackService.findByUserId(userId, page, size);
    }

    /**
     * 根据日期范围查询反馈记录
     */
    @GetMapping("/date-range")
    public Page<Feedback> getByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return feedbackService.findByDateRange(startDate, endDate, page, size);
    }

    /**
     * 根据菜单ID查询反馈记录
     */
    @GetMapping("/menu/{menuId}")
    public Page<Feedback> getByMenuId(
            @PathVariable Integer menuId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return feedbackService.findByMenuId(menuId, page, size);
    }

    /**
     * 根据批次号查询反馈记录
     */
    @GetMapping("/batch/{batchNumber}")
    public Page<Feedback> getByBatchNumber(
            @PathVariable String batchNumber,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return feedbackService.findByBatchNumber(batchNumber, page, size);
    }

    /**
     * 根据评分范围查询反馈记录
     */
    @GetMapping("/rating-range")
    public Page<Feedback> getByRatingRange(
            @RequestParam Integer minRating,
            @RequestParam Integer maxRating,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return feedbackService.findByRatingRange(minRating, maxRating, page, size);
    }

    /**
     * 统计评分分布
     */
    @GetMapping("/statistics/rating-distribution")
    public Map<Integer, Long> getRatingDistribution(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return feedbackService.calculateRatingDistribution(startDate, endDate);
    }

    /**
     * 获取平均评分
     */
    @GetMapping("/statistics/average-rating")
    public Double getAverageRating(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return feedbackService.calculateAverageRating(startDate, endDate);
    }

    /**
     * 获取热点反馈内容
     */
    @GetMapping("/statistics/hot-content")
    public Map<String, Long> getHotContent(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(defaultValue = "10") int limit) {
        return feedbackService.getHotFeedbackContent(startDate, endDate, limit);
    }

    /**
     * 新增反馈记录
     */
    @PostMapping
    public boolean save(@RequestBody Feedback feedback) {
        return feedbackService.save(feedback);
    }

    /**
     * 更新反馈记录
     */
    @PutMapping("/{id}")
    public boolean update(@PathVariable Integer id, @RequestBody Feedback feedback) {
        feedback.setId(id);
        return feedbackService.updateById(feedback);
    }

    /**
     * 删除反馈记录
     */
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id) {
        return feedbackService.removeById(id);
    }
} 