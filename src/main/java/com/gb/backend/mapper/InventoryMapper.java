package com.gb.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gb.backend.entity.Inventory;
import org.apache.ibatis.annotations.Mapper;

/**
 * 库存数据访问层
 */
@Mapper
public interface InventoryMapper extends BaseMapper<Inventory> {
} 