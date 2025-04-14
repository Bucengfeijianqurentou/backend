package com.gb.backend.service;

import com.gb.backend.entity.dto.TracingQueryDTO;
import com.gb.backend.entity.vo.TracingResultVO;

/**
 * 溯源查询服务接口
 */
public interface TracingService {
    
    /**
     * 执行溯源查询
     * 
     * @param queryDTO 查询参数
     * @return 溯源查询结果
     */
    TracingResultVO tracingQuery(TracingQueryDTO queryDTO);
} 