package com.gb.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gb.backend.entity.Grade;
import com.gb.backend.mapper.GradeMapper;
import com.gb.backend.service.GradeService;
import org.springframework.stereotype.Service;

/**
 * 年级服务实现类
 */
@Service
public class GradeServiceImpl extends ServiceImpl<GradeMapper, Grade> implements GradeService {

    @Override
    public Grade findByGradeName(String grade) {
        return getOne(new LambdaQueryWrapper<Grade>().eq(Grade::getGrade, grade));
    }
} 