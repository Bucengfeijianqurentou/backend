package com.gb.backend.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gb.backend.entity.Food;
import com.gb.backend.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 食品管理控制器
 */
@RestController
@RequestMapping("/api/foods")
@RequiredArgsConstructor
public class FoodController {
    
    private final FoodService foodService;

    /**
     * 分页查询食品列表
     */
    @GetMapping
    public Page<Food> list(@RequestParam(defaultValue = "1") int page,
                          @RequestParam(defaultValue = "10") int size) {
        return foodService.page(new Page<>(page, size));
    }

    /**
     * 根据ID查询食品
     */
    @GetMapping("/{id}")
    public Food getById(@PathVariable Integer id) {
        return foodService.getById(id);
    }

    /**
     * 根据名称查询食品
     */
    @GetMapping("/name/{name}")
    public Food getByName(@PathVariable String name) {
        return foodService.findByName(name);
    }

    /**
     * 新增食品
     */
    @PostMapping
    public boolean save(@RequestBody Food food) {
        return foodService.save(food);
    }

    /**
     * 更新食品信息
     */
    @PutMapping("/{id}")
    public boolean update(@PathVariable Integer id, @RequestBody Food food) {
        food.setId(id);
        return foodService.updateById(food);
    }

    /**
     * 删除食品
     */
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id) {
        return foodService.removeById(id);
    }
} 