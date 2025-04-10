package com.gb.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gb.backend.entity.Supplier;
import com.gb.backend.mapper.SupplierMapper;
import com.gb.backend.service.SupplierService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 供应商服务实现类
 */
@Service
public class SupplierServiceImpl extends ServiceImpl<SupplierMapper, Supplier> implements SupplierService {

    @Override
    public List<Supplier> getSuppliersByName(String name) {
        LambdaQueryWrapper<Supplier> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasText(name), Supplier::getName, name);
        return list(wrapper);
    }

    @Override
    public Page<Supplier> pageSupplier(int page, int size, String name, String contact) {
        Page<Supplier> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<Supplier> wrapper = new LambdaQueryWrapper<>();
        
        if (StringUtils.hasText(name)) {
            wrapper.like(Supplier::getName, name);
        }
        if (StringUtils.hasText(contact)) {
            wrapper.like(Supplier::getContact, contact);
        }
        
        return page(pageParam, wrapper);
    }

    @Override
    public boolean deleteSupplierBatch(List<Integer> ids) {
        return removeBatchByIds(ids);
    }
} 