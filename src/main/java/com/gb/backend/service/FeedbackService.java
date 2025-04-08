package com.gb.backend.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gb.backend.entity.Feedback;
import java.time.LocalDate;
import java.util.Map;

/**
 * 用户反馈服务接口
 */
public interface FeedbackService extends IService<Feedback> {
    /**
     * 根据用户ID查询反馈记录
     * @param userId 用户ID
     * @param page 页码
     * @param size 每页大小
     * @return 分页后的反馈记录
     */
    Page<Feedback> findByUserId(Integer userId, int page, int size);

    /**
     * 根据日期范围查询反馈记录
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param page 页码
     * @param size 每页大小
     * @return 分页后的反馈记录
     */
    Page<Feedback> findByDateRange(LocalDate startDate, LocalDate endDate, int page, int size);

    /**
     * 根据菜单ID查询反馈记录
     * @param menuId 菜单ID
     * @param page 页码
     * @param size 每页大小
     * @return 分页后的反馈记录
     */
    Page<Feedback> findByMenuId(Integer menuId, int page, int size);

    /**
     * 根据批次号查询反馈记录
     * @param batchNumber 批次号
     * @param page 页码
     * @param size 每页大小
     * @return 分页后的反馈记录
     */
    Page<Feedback> findByBatchNumber(String batchNumber, int page, int size);

    /**
     * 根据评分范围查询反馈记录
     * @param minRating 最小评分
     * @param maxRating 最大评分
     * @param page 页码
     * @param size 每页大小
     * @return 分页后的反馈记录
     */
    Page<Feedback> findByRatingRange(Integer minRating, Integer maxRating, int page, int size);

    /**
     * 统计某个时间段内的评分分布
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 各评分的数量统计
     */
    Map<Integer, Long> calculateRatingDistribution(LocalDate startDate, LocalDate endDate);

    /**
     * 计算某个时间段内的平均评分
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 平均评分
     */
    Double calculateAverageRating(LocalDate startDate, LocalDate endDate);

    /**
     * 获取某个时间段内的热点反馈内容
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param limit 返回数量
     * @return 反馈内容及其出现次数
     */
    Map<String, Long> getHotFeedbackContent(LocalDate startDate, LocalDate endDate, int limit);
} 