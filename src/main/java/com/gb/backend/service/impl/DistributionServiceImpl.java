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
    public Page<Distribution> getDistributionPage(int page, int size) {
        return page(new Page<>(page, size));
    }

    @Override
    public Page<Distribution> getDistributionByTimeRange(int page, int size, LocalDateTime startTime, LocalDateTime endTime) {
        LambdaQueryWrapper<Distribution> queryWrapper = new LambdaQueryWrapper<>();
        
        if (startTime != null) {
            queryWrapper.ge(Distribution::getDistributionTime, startTime);
        }
        
        if (endTime != null) {
            queryWrapper.le(Distribution::getDistributionTime, endTime);
        }
        
        // 按发放时间降序排序
        queryWrapper.orderByDesc(Distribution::getDistributionTime);
        
        return page(new Page<>(page, size), queryWrapper);
    }

    @Override
    public Page<Distribution> getDistributionByMenuId(int page, int size, Integer menuId) {
        LambdaQueryWrapper<Distribution> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Distribution::getMenuId, menuId)
                   .orderByDesc(Distribution::getDistributionTime);
        
        return page(new Page<>(page, size), queryWrapper);
    }

    @Override
    public boolean createDistribution(Distribution distribution) {
        // 设置发放时间为当前时间（如果未设置）
        if (distribution.getDistributionTime() == null) {
            distribution.setDistributionTime(LocalDateTime.now());
        }
        
        return save(distribution);
    }
}