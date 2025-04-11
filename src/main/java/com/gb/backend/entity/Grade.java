package com.gb.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 年级信息实体类
 */
@Data
@TableName("grades")
public class Grade {
    /**
     * 年级ID，主键，自动递增
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    
    /**
     * 年级名称
     */
    private String grade;
} 