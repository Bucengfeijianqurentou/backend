package com.gb.backend.controller;

import com.gb.backend.common.Result;
import com.gb.backend.entity.dto.TracingQueryDTO;
import com.gb.backend.entity.vo.TracingResultVO;
import com.gb.backend.service.TracingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 溯源查询控制器
 */
@RestController
@RequestMapping("/api/tracing")
@Slf4j
public class TracingController {

    @Autowired
    private TracingService tracingService;

    /**
     * 执行溯源查询
     *
     * @param queryDTO 查询参数
     * @return 溯源查询结果
     */
    @PostMapping("/query")
    public Result<TracingResultVO> query(@RequestBody TracingQueryDTO queryDTO) {
        log.info("溯源查询请求: {}", queryDTO);
        
        if (queryDTO.getDate() == null || queryDTO.getMealType() == null || queryDTO.getDishName() == null) {
            return Result.error("查询参数不完整，请提供日期、餐次和菜品名称");
        }
        
        TracingResultVO resultVO = tracingService.tracingQuery(queryDTO);
        
        if (!resultVO.isFound()) {
            return Result.error(resultVO.getErrorMessage());
        }
        
        return Result.success(resultVO);
    }
} 