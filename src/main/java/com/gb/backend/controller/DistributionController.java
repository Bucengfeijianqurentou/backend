package com.gb.backend.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gb.backend.common.Result;
import com.gb.backend.entity.Distribution;
import com.gb.backend.entity.dto.BatchDistributeDTO;
import com.gb.backend.entity.dto.DistributionDTO;
import com.gb.backend.service.DistributionService;
import com.gb.backend.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 食品发放管理控制器
 */
@RestController
@RequestMapping("/api/distributions")
@RequiredArgsConstructor
public class DistributionController {

    private final DistributionService distributionService;
    private final MenuService menuService;

    /**
     * 分页获取发放记录列表
     *
     * @param page 页码
     * @param size 每页记录数
     * @return 分页数据
     */
    @GetMapping
    public Result<Page<Distribution>> getDistributionList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Distribution> distributionPage = distributionService.getDistributionPage(page, size);
        return Result.success(distributionPage);
    }

    /**
     * 根据ID获取发放记录详情
     *
     * @param id 发放记录ID
     * @return 发放记录详情
     */
    @GetMapping("/{id}")
    public Result<Distribution> getDistributionById(@PathVariable Integer id) {
        Distribution distribution = distributionService.getById(id);
        if (distribution != null) {
            return Result.success(distribution);
        } else {
            return Result.error("发放记录不存在");
        }
    }

    /**
     * 创建发放记录
     *
     * @param distribution 发放记录信息
     * @return 创建结果
     */
    @PostMapping
    public Result<Boolean> createDistribution(@RequestBody Distribution distribution) {
        boolean success = distributionService.createDistribution(distribution);
        if (success) {
            return Result.success(true);
        } else {
            return Result.error("创建发放记录失败");
        }
    }

    /**
     * 更新发放记录
     *
     * @param id 发放记录ID
     * @param distribution 发放记录信息
     * @return 更新结果
     */
    @PutMapping("/{id}")
    public Result<Boolean> updateDistribution(
            @PathVariable Integer id,
            @RequestBody Distribution distribution) {
        distribution.setId(id);
        boolean success = distributionService.updateById(distribution);
        if (success) {
            return Result.success(true);
        } else {
            return Result.error("更新发放记录失败");
        }
    }

    /**
     * 删除发放记录
     *
     * @param id 发放记录ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public Result<Boolean> deleteDistribution(@PathVariable Integer id) {
        boolean success = distributionService.removeById(id);
        if (success) {
            return Result.success(true);
        } else {
            return Result.error("删除发放记录失败");
        }
    }

    /**
     * 根据时间范围查询发放记录
     *
     * @param page 页码
     * @param size 每页记录数
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 分页数据
     */
    @GetMapping("/time-range")
    public Result<Page<Distribution>> getDistributionByTimeRange(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        Page<Distribution> distributionPage = distributionService.getDistributionByTimeRange(page, size, startTime, endTime);
        return Result.success(distributionPage);
    }

    /**
     * 根据菜单ID查询发放记录
     *
     * @param menuId 菜单ID
     * @param page 页码
     * @param size 每页记录数
     * @return 分页数据
     */
    @GetMapping("/menu/{menuId}")
    public Result<Page<Distribution>> getDistributionByMenuId(
            @PathVariable Integer menuId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Distribution> distributionPage = distributionService.getDistributionByMenuId(page, size, menuId);
        return Result.success(distributionPage);
    }

    /**
     * 菜单发放，创建发放记录
     *
     * @param distributeDTO 发放信息（菜单ID、发放对象列表、发放人姓名）
     * @return 创建结果
     */
    @PostMapping("/batch")
    public Result<Boolean> createDistribution(@RequestBody BatchDistributeDTO distributeDTO) {
        // 获取菜单ID
        Integer menuId = distributeDTO.getMenuId();
        List<String> recipients = distributeDTO.getRecipients();
        String distributor = distributeDTO.getDistributor();
        
        // 验证菜单ID是否有效
        if (menuId == null || menuId <= 0) {
            return Result.error("无效的菜单ID");
        }
        
        // 验证发放对象列表
        if (recipients == null || recipients.isEmpty()) {
            return Result.error("发放对象列表不能为空");
        }
        
        // 验证发放人
        if (distributor == null || distributor.trim().isEmpty()) {
            return Result.error("发放人不能为空");
        }
        
        try {
            // 创建单条发放记录，将多个发放对象合并为一个字符串，用逗号分隔
            Distribution distribution = new Distribution();
            distribution.setMenuId(menuId);
            distribution.setDistributionTime(LocalDateTime.now());
            
            // 将多个发放对象合并为逗号分隔的字符串
            String recipientStr = String.join("，", recipients);
            distribution.setRecipient(recipientStr);
            
            // 设置发放人
            distribution.setDistributor(distributor);
            
            // 保存发放记录
            boolean success = distributionService.createDistribution(distribution);
            
            // 如果成功，更新菜单状态为"已发放"
            if (success) {
                menuService.updateMenuStatus(menuId, "2");
                return Result.success(true);
            } else {
                return Result.error("创建发放记录失败");
            }
        } catch (Exception e) {
            return Result.error("创建发放记录时发生错误: " + e.getMessage());
        }
    }



    /**
     * 分页获取带菜单信息的发放记录列表
     *
     * @param page 页码
     * @param size 每页记录数
     * @param startTime 开始时间（可选）
     * @param endTime 结束时间（可选）
     * @param menuId 菜单ID（可选）
     * @return 分页数据
     */
    @GetMapping("/with-menu")
    public Result<Page<DistributionDTO>> getDistributionWithMenu(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime,
            @RequestParam(required = false) Integer menuId) {
        Page<DistributionDTO> distributionPage = distributionService.getDistributionWithMenu(page, size, startTime, endTime, menuId);
        return Result.success(distributionPage);
    }



    /**
     * 根据ID获取带菜单信息的发放记录详情
     *
     * @param id 发放记录ID
     * @return 发放记录详情
     */
    @GetMapping("/with-menu/{id}")
    public Result<DistributionDTO> getDistributionWithMenuById(@PathVariable Integer id) {
        DistributionDTO distribution = distributionService.getDistributionWithMenuById(id);
        if (distribution != null) {
            return Result.success(distribution);
        } else {
            return Result.error("发放记录不存在");
        }
    }
}