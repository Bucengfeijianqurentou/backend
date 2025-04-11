package com.gb.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gb.backend.entity.Grade;
import org.apache.ibatis.annotations.Mapper;

/**
 * 年级数据访问层
 */
@Mapper
public interface GradeMapper extends BaseMapper<Grade> {
} 