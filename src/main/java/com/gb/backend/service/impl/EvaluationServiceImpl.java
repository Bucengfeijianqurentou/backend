package com.gb.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gb.backend.entity.Evaluation;
import com.gb.backend.mapper.EvaluationMapper;
import com.gb.backend.service.EvaluationService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 菜品评价服务实现类
 */
@Service
public class EvaluationServiceImpl extends ServiceImpl<EvaluationMapper, Evaluation> implements EvaluationService {

    @Override
    public Page<Evaluation> findByUserId(Integer userId, int page, int size) {
        return page(new Page<>(page, size),
                new LambdaQueryWrapper<Evaluation>()
                        .eq(Evaluation::getUserId, userId)
                        .orderByDesc(Evaluation::getSubmitTime));
    }

    @Override
    public Page<Evaluation> findByMenuId(Integer menuId, int page, int size) {
        return page(new Page<>(page, size),
                new LambdaQueryWrapper<Evaluation>()
                        .eq(Evaluation::getMenuId, menuId)
                        .orderByDesc(Evaluation::getSubmitTime));
    }

    @Override
    public Page<Evaluation> findByTimeRange(LocalDateTime startTime, LocalDateTime endTime, int page, int size) {
        return page(new Page<>(page, size),
                new LambdaQueryWrapper<Evaluation>()
                        .ge(Evaluation::getSubmitTime, startTime)
                        .le(Evaluation::getSubmitTime, endTime)
                        .orderByDesc(Evaluation::getSubmitTime));
    }

    @Override
    public Page<Evaluation> findByRatingRange(Integer minRating, Integer maxRating, int page, int size) {
        return page(new Page<>(page, size),
                new LambdaQueryWrapper<Evaluation>()
                        .ge(Evaluation::getRating, minRating)
                        .le(Evaluation::getRating, maxRating)
                        .orderByDesc(Evaluation::getSubmitTime));
    }

    @Override
    public Map<Integer, Long> calculateRatingDistribution(LocalDateTime startTime, LocalDateTime endTime) {
        return list(new LambdaQueryWrapper<Evaluation>()
                .ge(Evaluation::getSubmitTime, startTime)
                .le(Evaluation::getSubmitTime, endTime)
                .isNotNull(Evaluation::getRating))
                .stream()
                .collect(Collectors.groupingBy(
                        Evaluation::getRating,
                        Collectors.counting()
                ));
    }

    @Override
    public Double calculateAverageRatingByMenu(Integer menuId) {
        return list(new LambdaQueryWrapper<Evaluation>()
                .eq(Evaluation::getMenuId, menuId)
                .isNotNull(Evaluation::getRating))
                .stream()
                .mapToInt(Evaluation::getRating)
                .average()
                .orElse(0.0);
    }

    @Override
    public Double calculateAverageRating(LocalDateTime startTime, LocalDateTime endTime) {
        return list(new LambdaQueryWrapper<Evaluation>()
                .ge(Evaluation::getSubmitTime, startTime)
                .le(Evaluation::getSubmitTime, endTime)
                .isNotNull(Evaluation::getRating))
                .stream()
                .mapToInt(Evaluation::getRating)
                .average()
                .orElse(0.0);
    }
} 