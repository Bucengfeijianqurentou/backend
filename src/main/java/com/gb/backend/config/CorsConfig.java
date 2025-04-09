package com.gb.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

/**
 * 跨域配置
 * 允许所有来源的跨域请求
 * @since 2024-04-08
 */
@Configuration
public class CorsConfig {

    /**
     * 配置跨域过滤器
     *
     * @return CorsFilter
     */
    @Bean
    public CorsFilter corsFilter() {
        // 创建跨域配置
        CorsConfiguration config = new CorsConfiguration();
        
        // 允许所有域名
        config.addAllowedOriginPattern("*");
        // 允许所有请求头
        config.addAllowedHeader("*");
        // 允许所有请求方法
        config.addAllowedMethod("*");
        // 允许携带认证信息（cookie等）
        config.setAllowCredentials(true);
        // 暴露响应头
        config.addExposedHeader("*");
        
        // 创建基于URL的跨域配置源
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 对所有请求路径应用跨域配置
        source.registerCorsConfiguration("/**", config);
        
        return new CorsFilter(source);
    }


    /*CorsConfiguration config = new CorsConfiguration();

    // 1. 配置允许的域名（替换成实际的前端域名）
        config.setAllowedOrigins(Arrays.asList(
                "http://localhost:3000",     // React默认开发端口
                "http://localhost:8081",     // Vue默认开发端口
                "http://your-production-domain.com" // 生产环境域名
                ));

    // 2. 配置允许的请求方法
        config.setAllowedMethods(Arrays.asList(
                "GET",    // 查询
                "POST",   // 新增
                "PUT",    // 修改
                "DELETE", // 删除
                "OPTIONS" // 预检请求
                ));

    // 3. 配置允许的请求头
        config.setAllowedHeaders(Arrays.asList(
                "Authorization", // 认证信息
                "Content-Type", // 内容类型
                "X-Requested-With",
                "Accept"
                ));

    // 4. 配置暴露的响应头
        config.setExposedHeaders(Arrays.asList(
                "Authorization",
                "Content-Disposition" // 文件下载
                ));

    // 5. 允许携带认证信息
        config.setAllowCredentials(true);

    // 6. 预检请求的有效期，单位为秒
        config.setMaxAge(3600L);

    // 7. 配置不同路径的跨域策略
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

    // 7.1 对用户相关的API应用上述配置
        source.registerCorsConfiguration("/api/user/**", config);

    // 7.2 可以对其他API路径配置不同的跨域策略
    CorsConfiguration publicConfig = new CorsConfiguration();
        publicConfig.addAllowedOriginPattern("*"); // 公开接口允许所有域名访问
        publicConfig.addAllowedMethod("GET");      // 只允许GET请求
        publicConfig.addAllowedHeader("*");
        source.registerCorsConfiguration("/api/public/**", publicConfig);

        return new CorsFilter(source);*/

}

