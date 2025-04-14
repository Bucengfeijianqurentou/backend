package com.gb.backend.service.impl;

import com.gb.backend.entity.Inspection;
import com.gb.backend.entity.Menu;
import com.gb.backend.entity.Processing;
import com.gb.backend.entity.Purchase;
import com.gb.backend.entity.dto.TracingQueryDTO;
import com.gb.backend.entity.vo.TracingResultVO;
import com.gb.backend.mapper.TracingMapper;
import com.gb.backend.service.TracingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 溯源查询服务实现类
 */
@Service
@Slf4j
public class TracingServiceImpl implements TracingService {

    @Autowired
    private TracingMapper tracingMapper;

    @Override
    public TracingResultVO tracingQuery(TracingQueryDTO queryDTO) {
        TracingResultVO resultVO = new TracingResultVO();
        
        try {
            // 1. 根据日期、餐次和菜品名称查询菜单
            Menu menu = tracingMapper.findMenuByDateAndMealTypeAndDish(
                    queryDTO.getDate(), queryDTO.getMealType(), queryDTO.getDishName());
            
            if (menu == null) {
                resultVO.setErrorMessage("未找到符合条件的菜单记录");
                return resultVO;
            }
            
            resultVO.setMenu(menu);
            
            // 2. 根据菜单的processIds查询加工记录
            List<Processing> processingList = new ArrayList<>();
            if (StringUtils.hasText(menu.getProcessIds())) {
                List<Integer> processIdList = Arrays.stream(menu.getProcessIds().split(","))
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());
                
                processingList = tracingMapper.findProcessingsByIds(processIdList);
            }
            
            resultVO.setProcessingList(processingList);
            
            // 3. 根据加工记录的批次号查询采购记录
            List<Purchase> purchaseList = new ArrayList<>();
            for (Processing processing : processingList) {
                if (StringUtils.hasText(processing.getBatchNumber())) {
                    Purchase purchase = tracingMapper.findPurchaseByBatchNumber(processing.getBatchNumber());
                    if (purchase != null) {
                        purchaseList.add(purchase);
                    }
                }
            }
            
            resultVO.setPurchaseList(purchaseList);
            
            // 4. 根据菜单ID查询检查记录
            Inspection inspection = tracingMapper.findInspectionByMenuId(menu.getId());
            resultVO.setInspection(inspection);
            
            resultVO.setFound(true);
            
        } catch (Exception e) {
            log.error("溯源查询失败", e);
            resultVO.setErrorMessage("溯源查询失败: " + e.getMessage());
        }
        
        return resultVO;
    }
} 