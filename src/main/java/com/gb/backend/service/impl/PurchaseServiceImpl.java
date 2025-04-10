package com.gb.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gb.backend.entity.Purchase;
import com.gb.backend.mapper.PurchaseMapper;
import com.gb.backend.service.PurchaseService;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

/**
 * 采购服务实现类
 */
@Service
public class PurchaseServiceImpl extends ServiceImpl<PurchaseMapper, Purchase> implements PurchaseService {

    @Override
    public Purchase findByBatchNumber(String batchNumber) {
        return getOne(new LambdaQueryWrapper<Purchase>()
                .eq(Purchase::getBatchNumber, batchNumber));
    }

    @Override
    public Page<Purchase> findByDateRange(LocalDate startDate, LocalDate endDate, int page, int size) {
        return page(new Page<>(page, size),
                new LambdaQueryWrapper<Purchase>()
                        .ge(Purchase::getPurchaseDate, startDate)
                        .le(Purchase::getPurchaseDate, endDate)
                        .orderByDesc(Purchase::getPurchaseDate));
    }

    @Override
    public Page<Purchase> findBySupplier(String supplier, int page, int size) {
        return page(new Page<>(page, size),
                new LambdaQueryWrapper<Purchase>()
                        .eq(Purchase::getSupplier, supplier)
                        .orderByDesc(Purchase::getPurchaseDate));
    }

    @Override
    public Page<Purchase> findByPurchaserId(String purchaserId, int page, int size) {
        return page(new Page<>(page, size),
                new LambdaQueryWrapper<Purchase>()
                        .eq(Purchase::getPurchaserId, purchaserId)
                        .orderByDesc(Purchase::getPurchaseDate));
    }

    @Override
    public Page<Purchase> searchByKeyword(String keyword, int page, int size) {
        return page(new Page<>(page, size),
                new LambdaQueryWrapper<Purchase>()
                        .like(Purchase::getName, keyword)
                        .or()
                        .like(Purchase::getSupplier, keyword)
                        .orderByDesc(Purchase::getPurchaseDate));
    }
} 