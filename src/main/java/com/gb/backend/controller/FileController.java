package com.gb.backend.controller;

import com.gb.backend.annotation.PassToken;
import com.gb.backend.common.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 文件上传控制器
 */
@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {

    @Value("${file.upload.path}")
    private String uploadPath;

    /**
     * 单文件上传
     * @param file 上传的文件
     * @return 文件访问路径
     */
    @PassToken
    @PostMapping("/upload")
    public Result<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            // 检查上传目录是否存在，不存在则创建
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // 生成唯一的文件名
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String newFilename = UUID.randomUUID().toString() + extension;

            // 保存文件
            Path filePath = Paths.get(uploadPath, newFilename);
            Files.write(filePath, file.getBytes());

            // 返回文件访问路径（不需要/img前缀，因为已经在静态资源映射中配置）
            System.out.println("/" + newFilename);
            return Result.success("/" + newFilename);
        } catch (IOException e) {
            return Result.error("文件上传失败：" + e.getMessage());
        }
    }

    /**
     * 多文件上传
     * @param files 上传的文件列表
     * @return 文件访问路径列表
     */
    @PassToken
    @PostMapping("/batch")
    public Result<List<String>> uploadFiles(@RequestParam("files") MultipartFile[] files) {
        List<String> uploadedFiles = new ArrayList<>();
        
        try {
            // 检查上传目录是否存在，不存在则创建
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // 处理每个文件
            for (MultipartFile file : files) {
                // 生成唯一的文件名
                String originalFilename = file.getOriginalFilename();
                String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
                String newFilename = UUID.randomUUID().toString() + extension;

                // 保存文件
                Path filePath = Paths.get(uploadPath, newFilename);
                Files.write(filePath, file.getBytes());

                // 添加到已上传文件列表
                uploadedFiles.add("/" + newFilename);
            }

            return Result.success(uploadedFiles);
        } catch (IOException e) {
            return Result.error("文件上传失败：" + e.getMessage());
        }
    }

    /**
     * 删除文件
     * @param filename 文件名
     * @return 删除结果
     */
    @DeleteMapping("/{filename}")
    public Result<Boolean> deleteFile(@PathVariable String filename) {
        try {
            Path filePath = Paths.get(uploadPath, filename);
            return Result.success(Files.deleteIfExists(filePath));
        } catch (IOException e) {
            return Result.error("文件删除失败：" + e.getMessage());
        }
    }

    /**
     * 验证文件类型是否为图片
     * @param file 上传的文件
     * @return 是否为图片
     */
    private boolean isImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }
} 