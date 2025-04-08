package com.gb.backend.entity;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gb.backend.common.enums.HygieneCondition;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 食品加工记录实体
 */
@Data
@TableName("processings")
public class Processing {
    /**
     * 加工记录ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 采购记录ID
     */
    private Integer purchaseId;

    /**
     * 加工人员ID（食堂工作人员）
     */
    private Integer processorId;

    /**
     * 加工时间
     */
    private LocalDateTime processingTime;

    /**
     * 加工方法
     */
    private String method;

    /**
     * 加工数量
     */
    private Integer quantity;

    /**
     * 卫生条件
     */
    @EnumValue
    private HygieneCondition hygieneCondition;
} 