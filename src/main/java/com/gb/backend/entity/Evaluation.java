package com.gb.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 菜品评价实体
 */
@Data
@TableName("evaluation")
public class Evaluation {
    /**
     * 评价ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 菜单ID
     */
    private Integer menuId;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 评分(1-5星)
     */
    private Integer rating;

    /**
     * 评论内容
     */
    private String commentContent;

    /**
     * 提交时间
     */
    private LocalDateTime submitTime;
} 