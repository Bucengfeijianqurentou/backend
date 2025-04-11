package com.gb.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gb.backend.entity.Grade;

/**
 * 年级服务接口
 */
public interface GradeService extends IService<Grade> {
    /**
     * 根据年级名称查询
     * @param grade 年级名称
     * @return 年级信息
     */
    Grade findByGradeName(String grade);
} 