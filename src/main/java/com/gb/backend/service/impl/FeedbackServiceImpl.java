package com.gb.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gb.backend.entity.Feedback;
import com.gb.backend.mapper.FeedbackMapper;
import com.gb.backend.service.FeedbackService;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 用户反馈服务实现类
 */
@Service
public class FeedbackServiceImpl extends ServiceImpl<FeedbackMapper, Feedback> implements FeedbackService {

    @Override
    public Page<Feedback> pageFeedbacks(long current, long size) {
        Page<Feedback> page = new Page<>(current, size);
        LambdaQueryWrapper<Feedback> queryWrapper = new LambdaQueryWrapper<>();
        // 按反馈日期降序排列，最新的反馈优先显示
        queryWrapper.orderByDesc(Feedback::getFeedbackDate);
        return this.page(page, queryWrapper);
    }

    @Override
    public Page<Feedback> getFeedbacksByUserId(Integer userId, long current, long size) {
        Page<Feedback> page = new Page<>(current, size);
        LambdaQueryWrapper<Feedback> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Feedback::getUserId, userId);
        queryWrapper.orderByDesc(Feedback::getFeedbackDate);
        return this.page(page, queryWrapper);
    }

    @Override
    public Page<Feedback> getFeedbacksByDateRange(LocalDate startDate, LocalDate endDate, long current, long size) {
        Page<Feedback> page = new Page<>(current, size);
        LambdaQueryWrapper<Feedback> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ge(startDate != null, Feedback::getFeedbackDate, startDate);
        queryWrapper.le(endDate != null, Feedback::getFeedbackDate, endDate);
        queryWrapper.orderByDesc(Feedback::getFeedbackDate);
        return this.page(page, queryWrapper);
    }

    @Override
    public Map<String, Object> getFeedbackStatistics() {
        Map<String, Object> statistics = new HashMap<>();
        
        // 总反馈数
        long totalCount = this.count();
        statistics.put("totalCount", totalCount);
        
        // 今日反馈数
        LocalDate today = LocalDate.now();
        long todayCount = this.count(new LambdaQueryWrapper<Feedback>()
                .eq(Feedback::getFeedbackDate, today));
        statistics.put("todayCount", todayCount);
        
        // 近7天反馈数
        LocalDate sevenDaysAgo = today.minusDays(7);
        long last7DaysCount = this.count(new LambdaQueryWrapper<Feedback>()
                .ge(Feedback::getFeedbackDate, sevenDaysAgo)
                .le(Feedback::getFeedbackDate, today));
        statistics.put("last7DaysCount", last7DaysCount);
        
        // 近30天反馈数
        LocalDate thirtyDaysAgo = today.minusDays(30);
        long last30DaysCount = this.count(new LambdaQueryWrapper<Feedback>()
                .ge(Feedback::getFeedbackDate, thirtyDaysAgo)
                .le(Feedback::getFeedbackDate, today));
        statistics.put("last30DaysCount", last30DaysCount);
        
        return statistics;
    }
} 