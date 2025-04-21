package com.gb.backend.chain.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import lombok.Data;

/**
 * 区块链配置属性类
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "chain")
public class ChainProperties {
    
    /**
     * 基础URL
     */
    private String baseUrl;
    
    /**
     * 地址配置
     */
    private Address address = new Address();
    
    /**
     * API配置
     */
    private Api api = new Api();
    
    /**
     * 合约配置
     */
    private Contract contract = new Contract();

    /**
     * 地址配置类
     */
    @Data
    public static class Address {
        /**
         * 管理员地址
         */
        private String admin;
        
        /**
         * 合约地址
         */
        private String contract;
    }

    /**
     * API配置类
     */
    @Data
    public static class Api {
        /**
         * 合约API
         */
        private String contract;
        
        /**
         * 链交易数API
         */
        private String chainNumber;
        
        /**
         * 节点管理API
         */
        private String nodeManage;
        
        /**
         * 私钥API
         */
        private String privateKey;
        
        /**
         * 区块高度API
         */
        private String blockNumber;
    }

    /**
     * 合约配置类
     */
    @Data
    public static class Contract {
        /**
         * 食堂监管合约ABI
         */
        private String canteenSupervisionAbi;
    }
} 