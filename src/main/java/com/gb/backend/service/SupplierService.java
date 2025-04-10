package com.gb.backend.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gb.backend.entity.Supplier;

import java.util.List;

/**
 * 供应商服务接口
 */
public interface SupplierService extends IService<Supplier> {
    
    /**
     * 根据供应商名称模糊查询
     * @param name 供应商名称
     * @return 供应商列表
     */
    List<Supplier> getSuppliersByName(String name);
    
    /**
     * 分页查询供应商列表
     * @param page 页码
     * @param size 每页大小
     * @param name 供应商名称（可选）
     * @param contact 联系人（可选）
     * @return 分页后的供应商列表
     */
    Page<Supplier> pageSupplier(int page, int size, String name, String contact);
    
    /**
     * 批量删除供应商
     * @param ids 供应商ID列表
     * @return 是否删除成功
     */
    boolean deleteSupplierBatch(List<Integer> ids);
} 