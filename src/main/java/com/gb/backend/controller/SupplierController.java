package com.gb.backend.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gb.backend.common.Result;
import com.gb.backend.entity.Supplier;
import com.gb.backend.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 供应商控制器
 */
@RestController
@RequestMapping("/api/supplier")
@RequiredArgsConstructor
public class SupplierController {
    
    private final SupplierService supplierService;

    /**
     * 获取所有供应商列表（不分页）
     * @return 所有供应商列表
     */
    @GetMapping("/all")
    public Result<List<Supplier>> getAllSuppliers() {
        return Result.success(supplierService.list());
    }

    /**
     * 创建供应商
     * @param supplier 供应商信息
     * @return 创建后的供应商
     */
    @PostMapping
    public Supplier createSupplier(@RequestBody Supplier supplier) {
        supplierService.save(supplier);
        return supplier;
    }

    /**
     * 更新供应商
     * @param id 供应商ID
     * @param supplier 供应商信息
     * @return 更新后的供应商
     */
    @PutMapping("/{id}")
    public Supplier updateSupplier(@PathVariable Integer id, @RequestBody Supplier supplier) {
        supplier.setId(id);
        supplierService.updateById(supplier);
        return supplier;
    }

    /**
     * 删除供应商
     * @param id 供应商ID
     * @return 是否删除成功
     */
    @DeleteMapping("/{id}")
    public boolean deleteSupplier(@PathVariable Integer id) {
        return supplierService.removeById(id);
    }

    /**
     * 批量删除供应商
     * @param ids 供应商ID列表
     * @return 是否删除成功
     */
    @DeleteMapping("/batch")
    public boolean deleteSupplierBatch(@RequestBody List<Integer> ids) {
        return supplierService.deleteSupplierBatch(ids);
    }

    /**
     * 获取指定供应商
     * @param id 供应商ID
     * @return 供应商信息
     */
    @GetMapping("/{id}")
    public Supplier getSupplier(@PathVariable Integer id) {
        return supplierService.getById(id);
    }

    /**
     * 根据名称查询供应商
     * @param name 供应商名称
     * @return 供应商列表
     */
    @GetMapping("/by-name")
    public List<Supplier> getSuppliersByName(@RequestParam String name) {
        return supplierService.getSuppliersByName(name);
    }

    /**
     * 分页查询供应商列表
     * @param page 页码
     * @param size 每页大小
     * @param name 供应商名称（可选）
     * @param contact 联系人（可选）
     * @return 分页后的供应商列表
     */
    @GetMapping("/page")
    public Page<Supplier> pageSupplier(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String contact) {
        return supplierService.pageSupplier(page, size, name, contact);
    }
} 