package com.gb.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 供应商实体
 */
@Data
@TableName("suppliers")
public class Supplier {
    /**
     * 供应商ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    
    /**
     * 供应商名称
     */
    private String name;

    /**
     * 联系方式
     */
    private String contact;
} 