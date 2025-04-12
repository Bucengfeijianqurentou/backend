package com.gb.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gb.backend.entity.Distribution;
import com.gb.backend.entity.dto.DistributionDTO;
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
        LambdaQueryWrapper<Distribution> wrapper = new LambdaQueryWrapper<>();
        if (startTime != null) {
            wrapper.ge(Distribution::getDistributionTime, startTime);
        }
        if (endTime != null) {
            wrapper.le(Distribution::getDistributionTime, endTime);
        }
        return page(new Page<>(page, size), wrapper);
    }

    @Override
    public Page<Distribution> getDistributionByMenuId(int page, int size, Integer menuId) {
        LambdaQueryWrapper<Distribution> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Distribution::getMenuId, menuId);
        return page(new Page<>(page, size), wrapper);
    }

    @Override
    public boolean createDistribution(Distribution distribution) {
        return save(distribution);
    }
    
    @Override
    public Page<DistributionDTO> getDistributionWithMenu(int page, int size, LocalDateTime startTime, LocalDateTime endTime, Integer menuId) {
        return this.baseMapper.selectDistributionWithMenu(new Page<>(page, size), startTime, endTime, menuId);
    }
    
    @Override
    public DistributionDTO getDistributionWithMenuById(Integer id) {
        return this.baseMapper.selectDistributionWithMenuById(id);
    }
}