package com.gb.backend.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gb.backend.annotation.DataOnChain;
import com.gb.backend.annotation.PassToken;
import com.gb.backend.entity.Feedback;
import com.gb.backend.service.FeedbackService;
import com.gb.backend.common.Result;
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
@PassToken
public class FeedbackController {
    
    private final FeedbackService feedbackService;

    /**
     * 分页获取反馈列表
     * @param current 当前页
     * @param size 每页记录数
     * @return 分页数据
     */
    @GetMapping("/page")
    public Result<Page<Feedback>> pageFeedbacks(
            @RequestParam(defaultValue = "1") long current,
            @RequestParam(defaultValue = "10") long size) {
        return Result.success(feedbackService.pageFeedbacks(current, size));
    }

    /**
     * 根据用户ID获取反馈
     * @param userId 用户ID
     * @param current 当前页
     * @param size 每页记录数
     * @return 分页数据
     */
    @GetMapping("/user/{userId}")
    public Result<Page<Feedback>> getFeedbacksByUserId(
            @PathVariable Integer userId,
            @RequestParam(defaultValue = "1") long current,
            @RequestParam(defaultValue = "10") long size) {
        return Result.success(feedbackService.getFeedbacksByUserId(userId, current, size));
    }

    /**
     * 根据日期范围获取反馈
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param current 当前页
     * @param size 每页记录数
     * @return 分页数据
     */
    @GetMapping("/date-range")
    public Result<Page<Feedback>> getFeedbacksByDateRange(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(defaultValue = "1") long current,
            @RequestParam(defaultValue = "10") long size) {
        return Result.success(feedbackService.getFeedbacksByDateRange(startDate, endDate, current, size));
    }

    /**
     * 获取反馈统计数据
     * @return 统计数据
     */
    @GetMapping("/statistics")
    public Result<Map<String, Object>> getFeedbackStatistics() {
        return Result.success(feedbackService.getFeedbackStatistics());
    }

    /**
     * 根据ID获取反馈
     * @param id 反馈ID
     * @return 反馈信息
     */
    @GetMapping("/{id}")
    public Result<Feedback> getFeedbackById(@PathVariable Integer id) {
        Feedback feedback = feedbackService.getById(id);
        return feedback != null ? Result.success(feedback) : Result.error("反馈不存在");
    }

    /**
     * 添加反馈
     * @param feedback 反馈信息
     * @return 操作结果
     */
    @PostMapping
    @DataOnChain
    public Result<Boolean> addFeedback(@RequestBody Feedback feedback) {
        // 如果未设置反馈日期，则使用当前日期
        if (feedback.getFeedbackDate() == null) {
            feedback.setFeedbackDate(LocalDate.now());
        }
        boolean success = feedbackService.save(feedback);
        return success ? Result.success(true) : Result.error("添加反馈失败");
    }

    /**
     * 更新反馈
     * @param id 反馈ID
     * @param feedback 反馈信息
     * @return 操作结果
     */
    @PutMapping("/{id}")
    public Result<Boolean> updateFeedback(@PathVariable Integer id, @RequestBody Feedback feedback) {
        feedback.setId(id);
        boolean success = feedbackService.updateById(feedback);
        return success ? Result.success(true) : Result.error("更新反馈失败");
    }

    /**
     * 删除反馈
     * @param id 反馈ID
     * @return 操作结果
     */
    @DeleteMapping("/{id}")
    public Result<Boolean> deleteFeedback(@PathVariable Integer id) {
        boolean success = feedbackService.removeById(id);
        return success ? Result.success(true) : Result.error("删除反馈失败");
    }
} 