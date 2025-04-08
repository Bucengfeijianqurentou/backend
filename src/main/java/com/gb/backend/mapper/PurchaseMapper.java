package com.gb.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gb.backend.entity.Purchase;
import org.apache.ibatis.annotations.Mapper;

/**
 * 采购数据访问层
 */
@Mapper
public interface PurchaseMapper extends BaseMapper<Purchase> {
} 