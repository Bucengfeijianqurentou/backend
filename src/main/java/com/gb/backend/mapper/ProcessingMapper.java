package com.gb.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gb.backend.entity.Processing;
import org.apache.ibatis.annotations.Mapper;

/**
 * 食品加工数据访问层
 */
@Mapper
public interface ProcessingMapper extends BaseMapper<Processing> {
} 