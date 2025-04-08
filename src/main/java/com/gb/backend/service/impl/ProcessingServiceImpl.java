package com.gb.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gb.backend.entity.Processing;
import com.gb.backend.mapper.ProcessingMapper;
import com.gb.backend.service.ProcessingService;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

/**
 * 食品加工服务实现类
 */
@Service
public class ProcessingServiceImpl extends ServiceImpl<ProcessingMapper, Processing> implements ProcessingService {

    @Override
    public Page<Processing> findByPurchaseId(Integer purchaseId, int page, int size) {
        return page(new Page<>(page, size),
                new LambdaQueryWrapper<Processing>()
                        .eq(Processing::getPurchaseId, purchaseId)
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
} 