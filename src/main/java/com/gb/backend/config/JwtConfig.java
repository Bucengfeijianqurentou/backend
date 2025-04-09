package com.gb.backend.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * JWT配置类
 *
 * @author Claude
 * @since 2024-04-08
 */
@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {
    /**
     * 密钥
     */
    private String secretKey;
    
    /**
     * token有效期（毫秒）
     */
    private long expiration;
    
    /**
     * token签发者
     */
    private String issuer;
    
    /**
     * token在header中的名称
     */
    private String header;
    
    /**
     * token前缀
     */
    private String tokenPrefix;
} 