package com.gb.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 食品发放记录实体
 */
@Data
@TableName("distributions")
public class Distribution {
    /**
     * 发放记录ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 加工记录ID
     */
    private Integer processingId;

    /**
     * 食品ID
     */
    private Integer foodId;

    /**
     * 发放时间
     */
    private LocalDateTime distributionTime;

    /**
     * 发放数量
     */
    private Integer quantity;

    /**
     * 发放对象（如"初一年级"）
     */
    private String recipient;
} 