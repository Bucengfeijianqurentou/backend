package com.gb.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 食品实体
 */
@Data
@TableName("foods")
public class Food {
    /**
     * 食品ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    
    /**
     * 食品名称
     */
    private String name;

    /**
     * 食品描述
     */
    private String description;
} 