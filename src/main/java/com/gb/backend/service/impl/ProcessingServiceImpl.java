package com.gb.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gb.backend.entity.Processing;
import com.gb.backend.mapper.ProcessingMapper;
import com.gb.backend.service.ProcessingService;
import com.gb.backend.common.enums.HygieneCondition;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 食品加工服务实现类
 */
@Service
public class ProcessingServiceImpl extends ServiceImpl<ProcessingMapper, Processing> implements ProcessingService {

    @Override
    public Page<Processing> findByBatchNumber(String batchNumber, int page, int size) {
        return page(new Page<>(page, size),
                new LambdaQueryWrapper<Processing>()
                        .eq(Processing::getBatchNumber, batchNumber)
                        .orderByDesc(Processing::getProcessingTime));
    }

    @Override
    public Page<Processing> findByProcessorId(Integer processorId, int page, int size) {
        return page(new Page<>(page, size),
                new LambdaQueryWrapper<Processing>()
                        .eq(Processing::getProcessorId, processorId)
                        .orderByDesc(Processing::getProcessingTime));
    }

    @Override
    public Page<Processing> findByTimeRange(LocalDateTime startTime, LocalDateTime endTime, int page, int size) {
        return page(new Page<>(page, size),
                new LambdaQueryWrapper<Processing>()
                        .ge(Processing::getProcessingTime, startTime)
                        .le(Processing::getProcessingTime, endTime)
                        .orderByDesc(Processing::getProcessingTime));
    }

    @Override
    public Page<Processing> findByHygieneCondition(HygieneCondition condition, int page, int size) {
        return page(new Page<>(page, size),
                new LambdaQueryWrapper<Processing>()
                        .eq(Processing::getHygieneCondition, condition)
                        .orderByDesc(Processing::getProcessingTime));
    }

    @Override
    public Page<Processing> findByMethod(String method, int page, int size) {
        return page(new Page<>(page, size),
                new LambdaQueryWrapper<Processing>()
                        .eq(Processing::getMethod, method)
                        .orderByDesc(Processing::getProcessingTime));
    }

    @Override
    public Map<HygieneCondition, Long> calculateHygieneConditionDistribution(LocalDateTime startTime, LocalDateTime endTime) {
        // 查询指定时间范围内的所有加工记录
        LambdaQueryWrapper<Processing> queryWrapper = new LambdaQueryWrapper<Processing>()
                .ge(Processing::getProcessingTime, startTime)
                .le(Processing::getProcessingTime, endTime);
        
        // 使用Stream API进行统计
        return list(queryWrapper).stream()
                .collect(Collectors.groupingBy(
                        Processing::getHygieneCondition,
                        Collectors.counting()
                ));
    }
} 