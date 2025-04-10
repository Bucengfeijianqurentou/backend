package com.gb.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gb.backend.entity.Menu;
import org.apache.ibatis.annotations.Mapper;

/**
 * 菜单Mapper接口
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {
} 