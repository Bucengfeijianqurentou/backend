package com.gb.backend.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gb.backend.entity.Feedback;
import java.time.LocalDate;
import java.util.Map;

/**
 * 用户反馈服务接口
 */
public interface FeedbackService extends IService<Feedback> {
    /**
     * 分页查询反馈信息
     * @param current 当前页
     * @param size 每页大小
     * @return 分页结果
     */
    Page<Feedback> pageFeedbacks(long current, long size);
    
    /**
     * 根据用户ID查询反馈
     * @param userId 用户ID
     * @param current 当前页
     * @param size 每页大小
     * @return 分页结果
     */
    Page<Feedback> getFeedbacksByUserId(Integer userId, long current, long size);
    
    /**
     * 根据日期范围查询反馈
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param current 当前页
     * @param size 每页大小
     * @return 分页结果
     */
    Page<Feedback> getFeedbacksByDateRange(LocalDate startDate, LocalDate endDate, long current, long size);
    
    /**
     * 获取反馈统计数据
     * @return 反馈统计
     */
    Map<String, Object> getFeedbackStatistics();
} 