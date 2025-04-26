package com.gb.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gb.backend.entity.Evaluation;
import org.apache.ibatis.annotations.Mapper;

/**
 * 菜品评价数据访问层
 */
@Mapper
public interface EvaluationMapper extends BaseMapper<Evaluation> {
} 