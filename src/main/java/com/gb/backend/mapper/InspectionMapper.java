package com.gb.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gb.backend.entity.Inspection;
import org.apache.ibatis.annotations.Mapper;

/**
 * 监管检查数据访问层
 */
@Mapper
public interface InspectionMapper extends BaseMapper<Inspection> {
} 