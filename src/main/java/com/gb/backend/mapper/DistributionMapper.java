package com.gb.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gb.backend.entity.Distribution;
import org.apache.ibatis.annotations.Mapper;

/**
 * 食品发放数据访问层
 */
@Mapper
public interface DistributionMapper extends BaseMapper<Distribution> {
} 