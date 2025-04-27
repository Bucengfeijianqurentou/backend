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
     * 反馈ID，主键，自动递增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    
    /**
     * 用户ID，外键，关联users表的id
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
} 