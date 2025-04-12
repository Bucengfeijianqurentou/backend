package com.gb.backend.config;

import com.gb.backend.config.converter.StringToMealTypeConverter;
import com.gb.backend.config.converter.StringToMenuStatusConverter;
import com.gb.backend.interceptor.JwtInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web配置类
 * 用于注册拦截器等Web相关配置
 * @since 2024-04-08
 */
@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final JwtInterceptor jwtInterceptor;
    private final StringToMealTypeConverter stringToMealTypeConverter;
    private final StringToMenuStatusConverter stringToMenuStatusConverter;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**")  // 拦截所有请求
                .excludePathPatterns(    // 排除不需要拦截的路径
                        "/api/user/login",
                        "/api/user/register",
                        "/error",
                        "/swagger-ui/**",
                        "/v3/api-docs/**"
                );
    }

    /**
     * 添加自定义转换器，支持字符串到枚举的自动转换
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        // 添加自定义的字符串到餐次类型转换器
        registry.addConverter(stringToMealTypeConverter);
        // 添加自定义的字符串到菜单状态转换器
        registry.addConverter(stringToMenuStatusConverter);
    }
} 