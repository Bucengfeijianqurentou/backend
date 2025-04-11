package com.gb.backend.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gb.backend.common.Result;
import com.gb.backend.entity.Grade;
import com.gb.backend.service.GradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 年级管理控制器
 */
@RestController
@RequestMapping("/api/grades")
@RequiredArgsConstructor
public class GradeController {
    
    private final GradeService gradeService;
    
    /**
     * 获取所有年级
     * @return 年级列表
     */
    @GetMapping("/all")
    public Result<List<Grade>> getAllGrades() {
        return Result.success(gradeService.list());
    }
    
    /**
     * 分页查询年级列表
     * @param page 页码
     * @param size 每页大小
     * @return 分页后的年级列表
     */
    @GetMapping
    public Result<Page<Grade>> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return Result.success(gradeService.page(new Page<>(page, size)));
    }
    
    /**
     * 根据ID获取年级信息
     * @param id 年级ID
     * @return 年级信息
     */
    @GetMapping("/{id}")
    public Result<Grade> getById(@PathVariable Integer id) {
        Grade grade = gradeService.getById(id);
        if (grade != null) {
            return Result.success(grade);
        } else {
            return Result.error("年级不存在");
        }
    }
    
    /**
     * 根据年级名称查询
     * @param grade 年级名称
     * @return 年级信息
     */
    @GetMapping("/name/{grade}")
    public Result<Grade> getByGradeName(@PathVariable String grade) {
        Grade result = gradeService.findByGradeName(grade);
        if (result != null) {
            return Result.success(result);
        } else {
            return Result.error("年级不存在");
        }
    }
    
    /**
     * 创建年级
     * @param grade 年级信息
     * @return 创建结果
     */
    @PostMapping
    public Result<Grade> create(@RequestBody Grade grade) {
        // 检查是否已存在相同名称的年级
        Grade existingGrade = gradeService.findByGradeName(grade.getGrade());
        if (existingGrade != null) {
            return Result.error("年级名称已存在");
        }
        
        gradeService.save(grade);
        return Result.success(grade);
    }
    
    /**
     * 更新年级信息
     * @param id 年级ID
     * @param grade 年级信息
     * @return 更新结果
     */
    @PutMapping("/{id}")
    public Result<Grade> update(@PathVariable Integer id, @RequestBody Grade grade) {
        // 检查是否存在
        if (gradeService.getById(id) == null) {
            return Result.error("年级不存在");
        }
        
        // 检查是否与其他年级名称冲突
        Grade existingGrade = gradeService.findByGradeName(grade.getGrade());
        if (existingGrade != null && !existingGrade.getId().equals(id)) {
            return Result.error("年级名称已存在");
        }
        
        grade.setId(id);
        gradeService.updateById(grade);
        return Result.success(grade);
    }
    
    /**
     * 删除年级
     * @param id 年级ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@PathVariable Integer id) {
        // 检查是否存在
        if (gradeService.getById(id) == null) {
            return Result.error("年级不存在");
        }
        
        boolean removed = gradeService.removeById(id);
        if (removed) {
            return Result.success(true);
        } else {
            return Result.error("删除失败");
        }
    }
} 