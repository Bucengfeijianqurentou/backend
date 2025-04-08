package com.gb.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDate;

/**
 * 用户反馈实体
 */
@Data
@TableName("feedbacks")
public class Feedback {
    /**
     * 反馈ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户ID（学生/家长）
     */
    private Integer userId;

    /**
     * 反馈日期
     */
    private LocalDate feedbackDate;

    /**
     * 反馈内容
     */
    private String content;

    /**
     * 评分（1-5）
     */
    private Integer rating;

    /**
     * 菜单ID
     */
    private Integer menuId;

    /**
     * 批次号
     */
    private String batchNumber;
} 