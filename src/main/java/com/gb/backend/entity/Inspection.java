package com.gb.backend.entity;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gb.backend.common.enums.InspectionResult;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 监管检查记录实体
 */
@Data
@TableName("inspections")
public class Inspection {
    /**
     * 检查记录ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 监管人员ID
     */
    private Integer inspectorId;

    /**
     * 检查时间
     */
    private LocalDateTime inspectionTime;

    /**
     * 检查内容
     */
    private String content;

    /**
     * 检查结果
     */
    @EnumValue
    private InspectionResult result;

    /**
     * 发现的问题
     */
    private String issues;

    /**
     * 整改建议
     */
    private String suggestions;


    /**
     * 菜单ID
     */
    private Integer menuId;
    
    /**
     * 监察凭证图片路径
     */
    private String imagePath;


    /**
     * 交易哈希
     */
    private String transactionHash;
} 