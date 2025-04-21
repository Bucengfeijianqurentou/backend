package com.gb.backend.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.event.EventListener;

/**
 * 请求日志配置类
 * 负责启用AOP切面功能并在应用启动时显示日志
 */
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class RequestLoggingConfig {
    
    private static final Logger logger = LoggerFactory.getLogger(RequestLoggingConfig.class);
    
    /**
     * 应用启动后打印日志，表明请求日志切面已启用
     * @param event 应用启动事件
     */
    @EventListener
    public void onApplicationStarted(ApplicationStartedEvent event) {
        logger.info("==========================================================");
        logger.info("==== 请求日志切面已启用，将记录所有Controller方法的调用信息 ====");
        logger.info("==========================================================");
    }
} 