package com.gb.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gb.backend.entity.Distribution;
import com.gb.backend.mapper.DistributionMapper;
import com.gb.backend.service.DistributionService;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

/**
 * 食品发放服务实现类
 */
@Service
public class DistributionServiceImpl extends ServiceImpl<DistributionMapper, Distribution> implements DistributionService {

    @Override
    public Page<Distribution> findByProcessingId(Integer processingId, int page, int size) {
        return page(new Page<>(page, size),
                new LambdaQueryWrapper<Distribution>()
                        .eq(Distribution::getProcessingId, processingId)
                        .orderByDesc(Distribution::getDistributionTime));
    }

    @Override
    public Page<Distribution> findByFoodId(Integer foodId, int page, int size) {
        return page(new Page<>(page, size),
                new LambdaQueryWrapper<Distribution>()
                        .eq(Distribution::getFoodId, foodId)
                        .orderByDesc(Distribution::getDistributionTime));
    }

    @Override
    public Page<Distribution> findByTimeRange(LocalDateTime startTime, LocalDateTime endTime, int page, int size) {
        return page(new Page<>(page, size),
                new LambdaQueryWrapper<Distribution>()
                        .ge(Distribution::getDistributionTime, startTime)
                        .le(Distribution::getDistributionTime, endTime)
                        .orderByDesc(Distribution::getDistributionTime));
    }

    @Override
    public Page<Distribution> findByRecipient(String recipient, int page, int size) {
        return page(new Page<>(page, size),
                new LambdaQueryWrapper<Distribution>()
                        .eq(Distribution::getRecipient, recipient)
                        .orderByDesc(Distribution::getDistributionTime));
    }

    @Override
    public Integer calculateTotalQuantity(LocalDateTime startTime, LocalDateTime endTime, Integer foodId) {
        LambdaQueryWrapper<Distribution> wrapper = new LambdaQueryWrapper<Distribution>()
                .ge(Distribution::getDistributionTime, startTime)
                .le(Distribution::getDistributionTime, endTime);

        if (foodId != null) {
            wrapper.eq(Distribution::getFoodId, foodId);
        }

        return list(wrapper).stream()
                .mapToInt(Distribution::getQuantity)
                .sum();
    }
}