package com.gb.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gb.backend.entity.Inspection;
import com.gb.backend.mapper.InspectionMapper;
import com.gb.backend.service.InspectionService;
import com.gb.backend.common.enums.InspectionResult;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 监管检查服务实现类
 */
@Service
public class InspectionServiceImpl extends ServiceImpl<InspectionMapper, Inspection> implements InspectionService {

    @Override
    public Page<Inspection> findByInspectorId(Integer inspectorId, int page, int size) {
        return page(new Page<>(page, size),
                new LambdaQueryWrapper<Inspection>()
                        .eq(Inspection::getInspectorId, inspectorId)
                        .orderByDesc(Inspection::getInspectionTime));
    }

    @Override
    public Page<Inspection> findByTimeRange(LocalDateTime startTime, LocalDateTime endTime, int page, int size) {
        return page(new Page<>(page, size),
                new LambdaQueryWrapper<Inspection>()
                        .ge(Inspection::getInspectionTime, startTime)
                        .le(Inspection::getInspectionTime, endTime)
                        .orderByDesc(Inspection::getInspectionTime));
    }

    @Override
    public Page<Inspection> findByResult(InspectionResult result, int page, int size) {
        return page(new Page<>(page, size),
                new LambdaQueryWrapper<Inspection>()
                        .eq(Inspection::getResult, result)
                        .orderByDesc(Inspection::getInspectionTime));
    }


    @Override
    public Page<Inspection> findByMenuId(Integer menuId, int page, int size) {
        return page(new Page<>(page, size),
                new LambdaQueryWrapper<Inspection>()
                        .eq(Inspection::getMenuId, menuId)
                        .orderByDesc(Inspection::getInspectionTime));
    }

    @Override
    public Map<InspectionResult, Long> calculateResultDistribution(LocalDateTime startTime, LocalDateTime endTime) {
        return list(new LambdaQueryWrapper<Inspection>()
                .ge(Inspection::getInspectionTime, startTime)
                .le(Inspection::getInspectionTime, endTime))
                .stream()
                .collect(Collectors.groupingBy(
                        Inspection::getResult,
                        Collectors.counting()
                ));
    }

    @Override
    public Inspection getByMenuId(Integer id) {
        return this.getOne(new LambdaQueryWrapper<Inspection>()
                .eq(Inspection::getMenuId, id));
    }
}