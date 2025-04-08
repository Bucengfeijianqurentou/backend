package com.gb.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gb.backend.entity.Feedback;
import com.gb.backend.mapper.FeedbackMapper;
import com.gb.backend.service.FeedbackService;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 用户反馈服务实现类
 */
@Service
public class FeedbackServiceImpl extends ServiceImpl<FeedbackMapper, Feedback> implements FeedbackService {

    @Override
    public Page<Feedback> findByUserId(Integer userId, int page, int size) {
        return page(new Page<>(page, size),
                new LambdaQueryWrapper<Feedback>()
                        .eq(Feedback::getUserId, userId)
                        .orderByDesc(Feedback::getFeedbackDate));
    }

    @Override
    public Page<Feedback> findByDateRange(LocalDate startDate, LocalDate endDate, int page, int size) {
        return page(new Page<>(page, size),
                new LambdaQueryWrapper<Feedback>()
                        .ge(Feedback::getFeedbackDate, startDate)
                        .le(Feedback::getFeedbackDate, endDate)
                        .orderByDesc(Feedback::getFeedbackDate));
    }

    @Override
    public Page<Feedback> findByMenuId(Integer menuId, int page, int size) {
        return page(new Page<>(page, size),
                new LambdaQueryWrapper<Feedback>()
                        .eq(Feedback::getMenuId, menuId)
                        .orderByDesc(Feedback::getFeedbackDate));
    }

    @Override
    public Page<Feedback> findByBatchNumber(String batchNumber, int page, int size) {
        return page(new Page<>(page, size),
                new LambdaQueryWrapper<Feedback>()
                        .eq(Feedback::getBatchNumber, batchNumber)
                        .orderByDesc(Feedback::getFeedbackDate));
    }

    @Override
    public Page<Feedback> findByRatingRange(Integer minRating, Integer maxRating, int page, int size) {
        return page(new Page<>(page, size),
                new LambdaQueryWrapper<Feedback>()
                        .ge(Feedback::getRating, minRating)
                        .le(Feedback::getRating, maxRating)
                        .orderByDesc(Feedback::getFeedbackDate));
    }

    @Override
    public Map<Integer, Long> calculateRatingDistribution(LocalDate startDate, LocalDate endDate) {
        return list(new LambdaQueryWrapper<Feedback>()
                .ge(Feedback::getFeedbackDate, startDate)
                .le(Feedback::getFeedbackDate, endDate)
                .isNotNull(Feedback::getRating))
                .stream()
                .collect(Collectors.groupingBy(
                        Feedback::getRating,
                        Collectors.counting()
                ));
    }

    @Override
    public Double calculateAverageRating(LocalDate startDate, LocalDate endDate) {
        return list(new LambdaQueryWrapper<Feedback>()
                .ge(Feedback::getFeedbackDate, startDate)
                .le(Feedback::getFeedbackDate, endDate)
                .isNotNull(Feedback::getRating))
                .stream()
                .mapToInt(Feedback::getRating)
                .average()
                .orElse(0.0);
    }

    @Override
    public Map<String, Long> getHotFeedbackContent(LocalDate startDate, LocalDate endDate, int limit) {
        return list(new LambdaQueryWrapper<Feedback>()
                .ge(Feedback::getFeedbackDate, startDate)
                .le(Feedback::getFeedbackDate, endDate)
                .isNotNull(Feedback::getContent))
                .stream()
                .collect(Collectors.groupingBy(
                        Feedback::getContent,
                        Collectors.counting()
                ))
                .entrySet()
                .stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(limit)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        java.util.LinkedHashMap::new
                ));
    }
} 