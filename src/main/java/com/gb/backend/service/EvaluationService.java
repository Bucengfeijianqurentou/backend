package com.gb.backend.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gb.backend.entity.Evaluation;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 菜品评价服务接口
 */
public interface EvaluationService extends IService<Evaluation> {
    /**
     * 根据用户ID查询评价记录
     * @param userId 用户ID
     * @param page 页码
     * @param size 每页大小
     * @return 分页后的评价记录
     */
    Page<Evaluation> findByUserId(Integer userId, int page, int size);

    /**
     * 根据菜单ID查询评价记录
     * @param menuId 菜单ID
     * @param page 页码
     * @param size 每页大小
     * @return 分页后的评价记录
     */
    Page<Evaluation> findByMenuId(Integer menuId, int page, int size);

    /**
     * 根据时间范围查询评价记录
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param page 页码
     * @param size 每页大小
     * @return 分页后的评价记录
     */
    Page<Evaluation> findByTimeRange(LocalDateTime startTime, LocalDateTime endTime, int page, int size);

    /**
     * 根据评分范围查询评价记录
     * @param minRating 最小评分
     * @param maxRating 最大评分
     * @param page 页码
     * @param size 每页大小
     * @return 分页后的评价记录
     */
    Page<Evaluation> findByRatingRange(Integer minRating, Integer maxRating, int page, int size);

    /**
     * 统计某个时间段内的评分分布
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 各评分的数量统计
     */
    Map<Integer, Long> calculateRatingDistribution(LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 计算某个菜单的平均评分
     * @param menuId 菜单ID
     * @return 平均评分
     */
    Double calculateAverageRatingByMenu(Integer menuId);

    /**
     * 计算某个时间段内的平均评分
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 平均评分
     */
    Double calculateAverageRating(LocalDateTime startTime, LocalDateTime endTime);
} 