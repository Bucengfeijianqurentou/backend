package com.gb.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gb.backend.entity.Distribution;
import com.gb.backend.entity.dto.DistributionDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;

/**
 * 食品发放Mapper接口
 */
@Mapper
public interface DistributionMapper extends BaseMapper<Distribution> {


    /**
     * 查询发放记录及关联的菜单信息
     * @param page 分页参数
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param menuId 菜单ID
     * @return 分页结果
     */
    Page<DistributionDTO> selectDistributionWithMenu(Page<DistributionDTO> page, 
                                                   @Param("startTime") LocalDateTime startTime, 
                                                   @Param("endTime") LocalDateTime endTime, 
                                                   @Param("menuId") Integer menuId);

    
    /**
     * 根据ID查询发放记录及关联的菜单信息
     * @param id 发放记录ID
     * @return 发放记录DTO
     */
    DistributionDTO selectDistributionWithMenuById(@Param("id") Integer id);
} 