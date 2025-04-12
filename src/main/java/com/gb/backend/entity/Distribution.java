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
     * 菜单ID，外键，关联menus表的id
     */
    private Integer menuId;

    /**
     * 发放时间
     */
    private LocalDateTime distributionTime;

    /**
     * 发放对象（如"初一年级"）
     */
    private String recipient;
    
    /**
     * 发放人姓名
     */
    private String distributor;
} 